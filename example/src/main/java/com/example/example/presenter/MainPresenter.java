package com.example.example.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.example.R;
import com.example.example.activity.login.MainActivity;
import com.example.example.contract.MainContract;
import com.example.example.db.CloudDevice;
import com.example.example.db.LocalUser;
import com.example.example.engine.AppConstantInfo;
import com.example.example.engine.AppConstants;
import com.example.example.engine.DataBaseEngine;
import com.example.example.engine.LoginUtil;
import com.example.example.engine.NetUrl;
import com.example.example.engine.RemoteService;
import com.example.example.interfaces.LoginSuccessCallback;
import com.example.example.utils.NetUtils;
import com.example.example.utils.RSAUtils;
import com.example.example.utils.Utils;
import com.example.examplelib.net.DefaultThreadPool;
import com.example.examplelib.net.RequestParameter;
import com.example.examplelib.utils.ToastUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainPresenter implements MainContract.Presenter {
    private static final int VERIFY_TOKEN = 0;
    private static final int UPDATE_DEVICE_SUCCESS = 1;
    private static final int VERIFY_TOKEN_FAILED = 2;
    private static final int GET_PORT_FAILED = 3;
    private static final int STREAM_ERROR = 4;
    private static final int GET_DEVICES_SUCCESS = 5;
    private static final int GET_DEVICES_TOKEN_SUCCESS = 6;

    private static final String RSA = "RSA";
    private static final String VERIFY_TOKEN_SEND_CODE = "0801";
    private static final String VERIFY_TOKEN_RETRUN_CODE = "0802";
    private static final String GET_PORT_SEND_CODE = "0803";
    private static final String GET_PORT_RETURN_CODE = "0804";

    private static final String PACKAGE_PREFIX = "00000000";
    private static final String PACKAGE_SUFFIX = "0000";
    private static final String PACKAGE_INDEX = "18";

    private final MainContract.View mTasksView;
    private final SoftReference<MainActivity> mActivity;

    private Socket mSocket;

    private UpdateCurrentDeviceRunnable mUpdateCurrentDeviceRunnable;

    private CloudDevice mCurrentDevice;
    private String mToken = "";

    private final Handler mHandler = new GetTransmitPortHandler(this);

    private LocalUser mCurrentUser;

    private static class GetTransmitPortHandler extends Handler {
        private final SoftReference<MainPresenter> mPresenter;

        GetTransmitPortHandler(MainPresenter presenter) {
            mPresenter = new SoftReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            MainPresenter presenter = mPresenter.get();
            switch (what) {
                case GET_PORT_FAILED:
                    Toast.makeText(presenter.mActivity.get(), R.string.get_port_failed, Toast.LENGTH_SHORT).show();
                    break;

                case VERIFY_TOKEN_FAILED:
                    Toast.makeText(presenter.mActivity.get(), R.string.verify_token_failed, Toast.LENGTH_SHORT).show();
                    break;

                case STREAM_ERROR:
                    Toast.makeText(presenter.mActivity.get(), R.string.stream_error, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public MainPresenter(MainActivity activity, MainContract.View tasksView) {
        mActivity = new SoftReference<>(activity);
        mTasksView = tasksView;
        mTasksView.setPresenter(this);

        mCurrentUser = AppConstantInfo.CURRENT_USER;
    }

    @Override
    public void handleActivityResult(int requestCode, Intent intent) {
        switch (requestCode) {
            case AppConstants.SELECT_SHORTCUTS:
                handleShortcut(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void handleShortcut(Intent intent) {
    }

    @Override
    public void setBack() {
        mTasksView.setBack();
    }

    @Override
    public Toolbar getToolbar() {
        return mTasksView.getToolbar();
    }

    private final Runnable mVerifyTokenRunnable = new Runnable() {
        @Override
        public void run() {
            DataOutputStream outputStream = null;
            DataInputStream in = null;
            try {
                InetAddress ip = InetAddress.getByName(NetUrl.ACCESS_IP);
                mSocket = new Socket(ip, NetUrl.ACCESS_PORT);
                LocalUser currentUser = DataBaseEngine.getInstance().getCurrentUser();
                JSONObject object = new JSONObject();
                object.put(AppConstants.CLOUD_UID, currentUser.getUser_id());
                object.put(AppConstants.TOKEN, currentUser.getToken());
                String json = object.toString();

                String sockectData = getSocketData(json, VERIFY_TOKEN_SEND_CODE);
                outputStream = new DataOutputStream(mSocket.getOutputStream());
                outputStream.write(2);
                outputStream.write(sockectData.trim().getBytes());
                outputStream.write(3);
                outputStream.flush();

                in = new DataInputStream(mSocket.getInputStream());
                byte[] stx = new byte[1];
                byte[] length = new byte[4];
                byte[] index = new byte[2];
                byte[] code = new byte[4];
                byte[] reserved = new byte[8];
                byte[] suffix = new byte[5];
                in.read(stx);
                in.read(length);
                in.read(index);
                in.read(code);
                in.read(reserved);
                byte[] jsonObj = new byte[Integer.parseInt(new String(length)) - 24];
                in.read(jsonObj);
                in.read(suffix);

                if (new String(code).equals(VERIFY_TOKEN_RETRUN_CODE)) {

                    JSONObject jsonObject = JSON.parseObject(new String(jsonObj));
                    if (jsonObject.get(AppConstants.CODE) != null
                            && jsonObject.get(AppConstants.CODE).equals(AppConstants.TYPE_FILE)) {
                        CloudDevice currentDevice = DataBaseEngine.getInstance().getCurrentCloudDevice();
                        JSONObject portObject = new JSONObject();
                        portObject.put(AppConstants.CLOUD_DID, currentDevice.getDid());
                        json = portObject.toString();

                        sockectData = getSocketData(json, GET_PORT_SEND_CODE);

                        outputStream.write(2);
                        outputStream.write(sockectData.trim().getBytes());
                        outputStream.write(3);
                        outputStream.flush();

                        in.read(stx);
                        in.read(length);
                        in.read(index);
                        in.read(code);
                        in.read(reserved);
                        jsonObj = new byte[Integer.parseInt(new String(length)) - 24];
                        in.read(jsonObj);

                        if (new String(code).equals(GET_PORT_RETURN_CODE)) {
                            Message msg = mHandler.obtainMessage();
                            msg.obj = new String(jsonObj);
                            msg.what = VERIFY_TOKEN;
                            mHandler.sendMessage(msg);
                        } else {
                            Message msg = mHandler.obtainMessage();
                            msg.what = GET_PORT_FAILED;
                            mHandler.sendMessage(msg);
                        }

                    } else {
                        //获取端口失败
                        Message msg = mHandler.obtainMessage();
                        msg.what = GET_PORT_FAILED;
                        mHandler.sendMessage(msg);
                    }
                } else {
                    //token验证失败
                    Message msg = mHandler.obtainMessage();
                    msg.what = VERIFY_TOKEN_FAILED;
                    mHandler.sendMessage(msg);
                }
            } catch (JSONException e) {
                Message msg = mHandler.obtainMessage();
                msg.what = VERIFY_TOKEN_FAILED;
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                Message msg = mHandler.obtainMessage();
                msg.what = STREAM_ERROR;
                mHandler.sendMessage(msg);
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }

                } catch (IOException e) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = STREAM_ERROR;
                    mHandler.sendMessage(msg);
                }
            }
        }
    };

    public String getSocketData(String json, String code) {
        StringBuffer stringBuffer = new StringBuffer();

        int len = json.length() + 24;
        if ((len + "").length() == 2) {
            stringBuffer.append("00");
        } else if ((len + "").length() == 3) {
            stringBuffer.append("0");
        }

        stringBuffer.append(len);
        stringBuffer.append(PACKAGE_INDEX);
        stringBuffer.append(code);
        stringBuffer.append(PACKAGE_PREFIX);
        stringBuffer.append(json);
        stringBuffer.append(PACKAGE_SUFFIX);

        return stringBuffer.toString();
    }

    public void getDeviceRandCode(final String did, final String certificate, final String key) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if ((jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE))) {
                        String randCode = jsonObject.getJSONObject(AppConstants.JSON_OBJ).getString(AppConstants.RAND_CODE);
                        getDeviceToken(did, randCode, certificate, key);
                    } else {
                        Toast.makeText(mActivity.get(), jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(mActivity.get(), R.string.json_parse_exception, Toast.LENGTH_SHORT).show();
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity.get(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        ArrayList<RequestParameter> params = new ArrayList<>();
        RequestParameter rp1 = new RequestParameter(AppConstants.CERTIFICATE, certificate);
        params.add(rp1);

        if (!NetUtils.isNetWorkConnected(mActivity.get())) {
            Toast.makeText(mActivity.get(), R.string.network_error, Toast.LENGTH_SHORT).show();
        } else {
            RemoteService.getInstance().invoke(mActivity.get(), AppConstants.KEY_CHECKCERTIFICATE, params,
                    callback, errorCallback);
        }
    }

    private void getDeviceToken(final String did, String randCode, String certificate, String key) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if ((jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE))) {
                        final JSONObject deviceObj = jsonObject.getJSONObject(AppConstants.JSON_OBJ);
                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                CloudDevice currentDevice = DataBaseEngine.getInstance().getCloudDevice(did, AppConstantInfo.CURRENT_USER.getUser_id());
                                currentDevice.setToken(deviceObj.getString(AppConstants.TOKEN));
                                DataBaseEngine.getInstance().updateCloudDevice(currentDevice.getDid(), currentDevice);
                                AppConstantInfo.CLOUD_DEVICE.setToken(currentDevice.getToken());
                                AppConstantInfo.CLOUD_DEVICE.setCertificate(currentDevice.getCertificate());
                                AppConstantInfo.CLOUD_DEVICE.setKey(currentDevice.getKey());
                                LocalUser currentUser = DataBaseEngine.getInstance().getCurrentUser();
                                currentUser.setUser_name(deviceObj.getString(AppConstants.LOCAL_USER_NAME));
                                AppConstantInfo.CURRENT_USER.setUser_name(deviceObj.getString(AppConstants.LOCAL_USER_NAME));
                                DataBaseEngine.getInstance().updateUserInfo(currentUser.getUser_id(), currentUser);

                                Message msg = mHandler.obtainMessage();
                                msg.what = GET_DEVICES_TOKEN_SUCCESS;
                                mHandler.sendMessage(msg);
                            }
                        });

                    } else {
                        Toast.makeText(mActivity.get(), jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(mActivity.get(), R.string.json_parse_exception, Toast.LENGTH_SHORT).show();
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mActivity.get(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        try {
            ArrayList<RequestParameter> params = new ArrayList<>();
            RequestParameter rp1 = new RequestParameter(AppConstants.DEVICE_RAND_CODE, randCode);
            byte[] buffer = Base64.decode(key.getBytes(), Base64.URL_SAFE);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            byte[] encryptByte = RSAUtils.encryptData(randCode.getBytes(), privateKey);
            String afterencrypt = Base64.encodeToString(encryptByte, Base64.URL_SAFE);

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(afterencrypt);
            String dest = m.replaceAll("");

            RequestParameter rp2 = new RequestParameter(AppConstants.DEVICE_ENCRY_CODE, dest);
            RequestParameter rp3 = new RequestParameter(AppConstants.CLOUD_NAME, DataBaseEngine.getInstance()
                    .getCurrentUser().getUser_id());
            RequestParameter rp4 = new RequestParameter(AppConstants.CERTIFICATE, certificate);
            params.add(rp1);
            params.add(rp2);
            params.add(rp3);
            params.add(rp4);

            if (!NetUtils.isNetWorkConnected(mActivity.get())) {
                Toast.makeText(mActivity.get(), R.string.network_error, Toast.LENGTH_SHORT).show();
            } else {
                RemoteService.getInstance().invoke(mActivity.get(), AppConstants.KEY_GETAUTHTOKEN, params,
                        callback, errorCallback);
            }
        } catch (InvalidKeySpecException e) {
            Toast.makeText(mActivity.get(), R.string.decompress_failed, Toast.LENGTH_SHORT).show();
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(mActivity.get(), R.string.decompress_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private class UpdateCurrentDeviceRunnable implements Runnable {
        private CloudDevice currentDevice;

        public UpdateCurrentDeviceRunnable(CloudDevice device) {
            currentDevice = device;
        }

        @Override
        public void run() {
            DataBaseEngine.getInstance().changeCurrentDevice();
            CloudDevice cloudDevice = DataBaseEngine.getInstance().getCloudDevice(currentDevice.getDid(), AppConstantInfo.CURRENT_USER.getUser_id());
            cloudDevice.setCurrent_device(true);
            DataBaseEngine.getInstance().updateCloudDevice(currentDevice.getDid(), cloudDevice);

            Message msg = mHandler.obtainMessage();
            msg.obj = cloudDevice;
            msg.what = UPDATE_DEVICE_SUCCESS;
            mHandler.sendMessage(msg);

        }
    }

    @Override
    public void getBoxName() {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (AppConstants.SUCCESS_CODE.equals(jsonObject.getString(AppConstants.CODE))) {
                        JSONObject object = jsonObject.getJSONObject(AppConstants.JSON_OBJ);
                        String boxName = object.getString(AppConstants.ACNAME);
                        mTasksView.setBoxName(boxName);
                    } else {
                        if (AppConstants.TOKEN_EXPIRED_CODE.equals(jsonObject.getString(AppConstants.CODE))
                                || AppConstants.CLOUD_DEVICE_TOKEN_EXPIRED_CODE.equals(jsonObject.getString(AppConstants.CODE))) {
                            LoginUtil.localLogin(mActivity.get(), null, new LoginSuccessCallback() {
                                @Override
                                public void onLoginSuccess() {
                                    getBoxName();
                                }
                            });
                        } else if (jsonObject.getString(AppConstants.CODE).equals(AppConstants.USER_NOT_EXIST_EXPIRED_CODE)
                                || jsonObject.getString(AppConstants.CODE).equals(AppConstants.PASSWORD_MODIFIED_TOKEN_EXPIRED_CODE)
                                || jsonObject.getString(AppConstants.CODE).equals(AppConstants.CLOUD_PASSWORD_MODIFIED_TOKEN_EXPIRED_CODE)) {
                            LoginUtil.deleteAllDevicesToLogin(mActivity.get());
                        } else {
                            ToastUtils.showToast(mActivity.get(), jsonObject.getString(AppConstants.MSG));
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(mActivity.get(), mActivity.get().getResources().getString(R.string.json_parse_exception), Toast.LENGTH_SHORT).show();
                }
            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };

        ArrayList<RequestParameter> params = new ArrayList<>();
        if (Utils.isCloudLogin()) {
            params.add(new RequestParameter(AppConstants.TOKEN, AppConstantInfo.CLOUD_DEVICE.getToken()));
        } else {
            params.add(new RequestParameter(AppConstants.TOKEN, AppConstantInfo.CURRENT_USER.getToken()));
        }
        if (!NetUtils.isNetWorkConnected(mActivity.get())) {
            Toast.makeText(mActivity.get(), R.string.network_error, Toast.LENGTH_SHORT).show();
        } else {
            RemoteService.getInstance().invoke(mActivity.get(), AppConstants.KEY_BOXNAME, params,
                    callback, errorCallback);
        }
    }
}
