package com.example.example.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.example.R;
import com.example.example.activity.login.LoginActivity;
import com.example.example.activity.login.MainActivity;
import com.example.example.contract.LoginContract;
import com.example.example.db.LocalUser;
import com.example.example.engine.AppConstantInfo;
import com.example.example.engine.AppConstants;
import com.example.example.engine.DataBaseEngine;
import com.example.example.engine.RemoteService;
import com.example.example.utils.MD5Utils;
import com.example.example.utils.NetUtils;
import com.example.examplelib.net.DefaultThreadPool;
import com.example.examplelib.net.RequestParameter;
import com.example.examplelib.utils.PreferenceUtils;
import com.example.examplelib.utils.ToastUtils;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Listens to user actions from the UI, retrieves the data and updates the
 * UI as required.
 */
public class LoginPresenter implements LoginContract.Presenter {
    private static final int GET_DEVICE = 0;
    private static final int GET_DEVICE_FAILED = 1;
    private static final int CLOUD_ACCOUNT = 2;
    private static final int LOCAL_ACCOUNT = 3;

    private static final String GROUP_IP = "224.0.0.1";
    private static final int MULTICAST_PORT = 9999;
    private static final String GET_DEVICE_TAG = "SMARTHOMEv1.0";

    private final SoftReference<LoginActivity> mActivity;
    private final LoginContract.View taskView;
    private LocalUser mCurrentUser;

    private final Handler mHandler = new GetDeviceHandler(this);

    private String userName = "";
    private String userPasswprd = "";
    private String mToken = "";
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private GetDeviceRunnable getDeviceRunnable, getUserDeviceRunnable;

    private static class GetDeviceHandler extends Handler {
        private final SoftReference<LoginPresenter> mPresenter;

        GetDeviceHandler(LoginPresenter presenter) {
            mPresenter = new SoftReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            LoginPresenter presenter = mPresenter.get();
            switch (what) {
                case GET_DEVICE:
                    String result = (String) msg.obj;
                    try {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE)) {
                            String device = jsonObject.getString(AppConstants.JSON_OBJ);
                            String localDid = jsonObject.getJSONObject(AppConstants.JSON_OBJ).getString(AppConstants.CLOUD_DID);
                            PreferenceUtils.putString(presenter.mActivity.get(), AppConstants.CLOUD_DID, localDid);
                            if (msg.arg1 == 1) {
                                presenter.cloudAccountLogin(device);
                            } else {
                                presenter.cloudLogin(device);
                            }

                        } else {
                            Toast.makeText(presenter.mActivity.get(), jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        ToastUtils.showToast(presenter.mActivity.get(), presenter.mActivity.get().getString(R.string.json_parse_exception));
                    }

                    break;
                case GET_DEVICE_FAILED:
                    PreferenceUtils.putString(presenter.mActivity.get(), AppConstants.CLOUD_DID, "");
                    if (msg.arg1 == 1) {
                        presenter.cloudAccountLogin("");
                    } else {
                        presenter.cloudLogin("");
                    }
                    break;
                case CLOUD_ACCOUNT:
                    presenter.getCurrentDevice();
                    break;
                case LOCAL_ACCOUNT:
                    presenter.localAccountLogin();
                    break;
            }
        }
    }

    public void cloudAccountLogin(String device) {
        DefaultThreadPool.removeTaskFromQueue(getUserDeviceRunnable);
        DefaultThreadPool.removeTaskFromQueue(mGetCurrentUserRunnable);
        AppConstantInfo.CURRENT_USER = mCurrentUser;
        Intent intent = new Intent(mActivity.get(), MainActivity.class);
        intent.putExtra(AppConstants.TOKEN, mToken);
        intent.putExtra(AppConstants.IS_CLOUD_ACCOUNT, true);
        intent.putExtra(AppConstants.SELECTED_DEVICE, device);
        mActivity.get().startActivity(intent);
        mActivity.get().finish();
    }

    public void localAccountLogin() {
        DefaultThreadPool.removeTaskFromQueue(mGetCurrentUserRunnable);
        AppConstantInfo.CURRENT_USER = mCurrentUser;
        Intent intent = new Intent(mActivity.get(), MainActivity.class);
        mActivity.get().startActivity(intent);
        mActivity.get().finish();
    }

    public void getCurrentDevice() {
        getUserDeviceRunnable = new GetDeviceRunnable(1);
        DefaultThreadPool.getInstance().execute(getUserDeviceRunnable);
    }

    public LoginPresenter(LoginActivity activity, LoginContract.View tasksView) {
        mActivity = new SoftReference<>(activity);
        this.taskView = tasksView;
        tasksView.setPresenter(this);

        DefaultThreadPool.getInstance().execute(mGetCurrentUserRunnable);
    }

    private final Runnable mGetCurrentUserRunnable = new Runnable() {
        @Override
        public void run() {
            mCurrentUser = DataBaseEngine.getInstance().getCurrentUser();
            if (mCurrentUser != null) {
                String user_id = mCurrentUser.getUser_id();
                mToken = mCurrentUser.getToken();
                if (isMobileNO(user_id)) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = CLOUD_ACCOUNT;
                    mHandler.sendMessage(msg);
                } else {
                    Message msg = mHandler.obtainMessage();
                    msg.what = LOCAL_ACCOUNT;
                    mHandler.sendMessage(msg);
                }
            }
        }
    };

    private static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3578]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    public void login(final String userName, final String userPasswprd) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                taskView.cancelButtonSetting();
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE)) {
                        //登录成功
                        JSONObject userInfo = jsonObject.getJSONObject(AppConstants.JSON_OBJ);

                        String token = userInfo.getString(AppConstants.TOKEN);
                        final String userId = userInfo.getString(AppConstants.USER_ID);
                        String user_name = userInfo.getString(AppConstants.LOCAL_USER_NAME);
                        int login_flg = userInfo.getInteger(AppConstants.LOGIN_FLG);
                        int cloudType = userInfo.getInteger(AppConstants.CLOUD_TYPE);
                        String cloud_accout = userInfo.getString(AppConstants.CLOUD_USER_NAME);
                        String did = userInfo.getString(AppConstants.CLOUD_DID);
                        final LocalUser localUser = new LocalUser(userId, user_name, userPasswprd, cloud_accout, login_flg, cloudType, token, null, true, did);
                        AppConstantInfo.CURRENT_USER = new LocalUser(userId, user_name, userPasswprd, cloud_accout, login_flg, cloudType, token, null, true, did);
                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                DataBaseEngine.getInstance().changeCurrentUserInfo();
                                DataBaseEngine.getInstance().updateUserInfo(userId, localUser);
                            }
                        });
                        //跳转首页
                        Intent intent = new Intent(mActivity.get(), MainActivity.class);
                        mActivity.get().startActivity(intent);
                        mActivity.get().finish();
                    } else {
                        //登录失败
                        Toast.makeText(mActivity.get(), jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(mActivity.get(), mActivity.get().getString(R.string.json_parse_exception));
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskView.cancelButtonSetting();
                Toast.makeText(mActivity.get(), R.string.smarthome_network_error, Toast.LENGTH_SHORT).show();
            }
        };

        if (!NetUtils.isNetWorkConnected(mActivity.get())) {
            taskView.cancelButtonSetting();
            Toast.makeText(mActivity.get(), R.string.network_error, Toast.LENGTH_SHORT).show();
        } else {
            try {
                ArrayList<RequestParameter> params = new ArrayList<>();
                RequestParameter rp1 = new RequestParameter(AppConstants.USER_NAME, userName);
                RequestParameter rp2 = new RequestParameter(AppConstants.USER_PASSWORD, MD5Utils.getMD5Code(userPasswprd));
                params.add(rp1);
                params.add(rp2);

                RemoteService.getInstance().invoke(mActivity.get(), AppConstants.KEY_LOGIN, params,
                        callback, errorCallback);
            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(mActivity.get(), R.string.md5_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void cloudLogin(final String device) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                taskView.cancelButtonSetting();
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.getString(AppConstants.REQUEST_CODE) != null &&
                            !jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE)) {
                        Toast.makeText(mActivity.get(), jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                    } else {
                        JSONObject userInfo = (JSONObject) jsonObject.get(AppConstants.JSON_OBJ);
                        String token = userInfo.getString(AppConstants.TOKEN);
                        String refreshToken = userInfo.getString(AppConstants.REFRESH_TOKEN);

                        final LocalUser localUser = new LocalUser(userName, userName, userPasswprd, userName, 0, 0, token, refreshToken, true, "");

                        if (AppConstantInfo.CURRENT_USER != null && AppConstantInfo.CURRENT_USER.getUser_id().equals(userName)) {
                            AppConstantInfo.CURRENT_USER = new LocalUser(userName, AppConstantInfo.CURRENT_USER.getUser_name(), userPasswprd, userName, 0, 0, token, refreshToken, true, "");
                        } else {
                            AppConstantInfo.CURRENT_USER = new LocalUser(userName, userName, userPasswprd, userName, 0, 0, token, refreshToken, true, "");
                        }

                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                DataBaseEngine.getInstance().changeCurrentUserInfo();
                                DataBaseEngine.getInstance().updateUserInfo(userName, localUser);
                            }
                        });

                        DefaultThreadPool.removeTaskFromQueue(getDeviceRunnable);
                        Intent intent = new Intent(mActivity.get(), MainActivity.class);
                        intent.putExtra(AppConstants.TOKEN, token);
                        intent.putExtra(AppConstants.IS_CLOUD_ACCOUNT, true);
                        intent.putExtra(AppConstants.SELECTED_DEVICE, device);
                        mActivity.get().startActivity(intent);
                        mActivity.get().finish();
                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(mActivity.get(), mActivity.get().getString(R.string.json_parse_exception));
                }
            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                taskView.cancelButtonSetting();
                Toast.makeText(mActivity.get(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        if (!NetUtils.isNetWorkConnected(mActivity.get())) {
            taskView.cancelButtonSetting();
            Toast.makeText(mActivity.get(), R.string.network_error, Toast.LENGTH_SHORT).show();
        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) mActivity.get().getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();

                ArrayList<RequestParameter> params = new ArrayList<>();
                RequestParameter rp1 = new RequestParameter(AppConstants.CLOUD_UID, userName);
                RequestParameter rp2 = new RequestParameter(AppConstants.PASSWORD, MD5Utils.getMD5Code(userPasswprd));
                RequestParameter rp3 = new RequestParameter(AppConstants.GUID, imei);
                params.add(rp1);
                params.add(rp2);
                params.add(rp3);

                RemoteService.getInstance().invokeHeader(mActivity.get(), AppConstants.KEY_LOGINCLOUD, "application/x-www-form-urlencoded", null, null, params,
                        callback, errorCallback);
            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(mActivity.get(), R.string.md5_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void getDevice(String userName, String password) {
        this.userName = userName;
        this.userPasswprd = password;
        getDeviceRunnable = new GetDeviceRunnable(0);
        DefaultThreadPool.getInstance().execute(getDeviceRunnable);
    }

    private class GetDeviceRunnable implements Runnable {
        private int currentUserExist;

        public GetDeviceRunnable(int flag) {
            currentUserExist = flag;
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            try {
                MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT);
                multicastSocket.setLoopbackMode(true);
                multicastSocket.setSoTimeout(5000);
                InetAddress group = InetAddress.getByName(GROUP_IP);
                multicastSocket.joinGroup(group);
                byte[] sendData = GET_DEVICE_TAG.getBytes();
                DatagramPacket packet = new DatagramPacket(sendData, sendData.length, group, MULTICAST_PORT);
                multicastSocket.send(packet);
                byte[] receiveData = new byte[256];
                packet = new DatagramPacket(receiveData, receiveData.length);
                multicastSocket.receive(packet);
                String result = new String(receiveData, 0, packet.getLength());

                Message msg = mHandler.obtainMessage();
                msg.obj = result;
                msg.arg1 = currentUserExist;
                msg.what = GET_DEVICE;
                mHandler.sendMessage(msg);

            } catch (IOException e) {
                Message msg = mHandler.obtainMessage();
                msg.arg1 = currentUserExist;
                msg.what = GET_DEVICE_FAILED;
                mHandler.sendMessage(msg);
            }
        }
    }
}
