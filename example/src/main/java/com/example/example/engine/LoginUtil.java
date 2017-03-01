package com.example.example.engine;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.example.R;
import com.example.example.activity.login.LoginActivity;
import com.example.example.base.BaseService;
import com.example.example.db.CloudDevice;
import com.example.example.db.LocalUser;
import com.example.example.interfaces.LoginSuccessCallback;
import com.example.example.utils.MD5Utils;
import com.example.example.utils.NetUtils;
import com.example.example.utils.RSAUtils;
import com.example.example.utils.Utils;
import com.example.examplelib.activity.BaseActivity;
import com.example.examplelib.net.DefaultThreadPool;
import com.example.examplelib.net.RequestParameter;
import com.example.examplelib.utils.ToastUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginUtil {
    private static final int GET_DEVICE_LIST_SUCCESS = 0;
    private static final int GET_CERTIFICATE_SUCCESS = 1;
    private static final int DELETE_DEVICES_SUCCESS = 2;

    private static BaseActivity activity;
    private static LoginSuccessCallback mLoginSuccessCallback;
    private static Context mContext;

    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DEVICE_LIST_SUCCESS:
                    List<CloudDevice> list = (List<CloudDevice>) msg.obj;
                    getCertificates(list, activity);
                    break;

                case GET_CERTIFICATE_SUCCESS:
                    getDeviceRandCode(activity, null, AppConstantInfo.CLOUD_DEVICE.getDid(), AppConstantInfo.CLOUD_DEVICE.getCertificate(),
                            AppConstantInfo.CLOUD_DEVICE.getKey());
                    break;
            }
        }
    };

    public static void localLogin(final BaseActivity mActivity, final BaseService service, final LoginSuccessCallback loginSuccessCallback) {
        if (Utils.isCloudLogin()) {
            String did = AppConstantInfo.CLOUD_DEVICE.getDid();
            String certificate = AppConstantInfo.CLOUD_DEVICE.getCertificate();
            String key = AppConstantInfo.CLOUD_DEVICE.getKey();
            getDeviceRandCode(mActivity, service, did, certificate, key);
        } else {
            getlocalToken(mActivity, service, loginSuccessCallback);
        }
    }

    public static void getlocalToken(final BaseActivity mActivity, final BaseService service, final LoginSuccessCallback loginSuccessCallback) {
        final String localUserName = AppConstantInfo.CURRENT_USER.getUser_name();
        final String localPasword = AppConstantInfo.CURRENT_USER.getUser_password();

        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        final LocalUser localUser = new LocalUser(userId, user_name, localPasword, cloud_accout, login_flg, cloudType, token, null, true, did);
                        AppConstantInfo.CURRENT_USER = new LocalUser(userId, user_name, localPasword, cloud_accout, login_flg, cloudType, token, null, true, did);
                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                DataBaseEngine.getInstance().changeCurrentUserInfo();
                                DataBaseEngine.getInstance().updateUserInfo(userId, localUser);
                            }
                        });

                        loginSuccessCallback.onLoginSuccess();

                    } else {
                        //登录失败
                        if (mActivity != null) {
                            Toast.makeText(mActivity, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(service, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    if (mActivity != null) {
                        ToastUtils.showToast(mActivity, mActivity.getString(R.string.json_parse_exception));
                    } else {
                        Toast.makeText(service, service.getString(R.string.json_parse_exception), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mActivity != null) {
                    Toast.makeText(mActivity, R.string.smarthome_network_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(service, service.getString(R.string.smarthome_network_error), Toast.LENGTH_SHORT).show();
                }
            }
        };

        if (mActivity != null) {
            if (!NetUtils.isNetWorkConnected(mActivity)) {
                Toast.makeText(mActivity, R.string.network_error, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    ArrayList<RequestParameter> params = new ArrayList<>();
                    RequestParameter rp1 = new RequestParameter(AppConstants.USER_NAME, localUserName);
                    RequestParameter rp2 = new RequestParameter(AppConstants.USER_PASSWORD, MD5Utils.getMD5Code(localPasword));
                    params.add(rp1);
                    params.add(rp2);

                    RemoteService.getInstance().invoke(mActivity, AppConstants.KEY_LOGIN, params,
                            callback, errorCallback);
                } catch (NoSuchAlgorithmException e) {
                    Toast.makeText(mActivity, R.string.md5_error, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (!NetUtils.isNetWorkConnected(service)) {
                Toast.makeText(service, R.string.network_error, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    ArrayList<RequestParameter> params = new ArrayList<>();
                    RequestParameter rp1 = new RequestParameter(AppConstants.USER_NAME, localUserName);
                    RequestParameter rp2 = new RequestParameter(AppConstants.USER_PASSWORD, MD5Utils.getMD5Code(localPasword));
                    params.add(rp1);
                    params.add(rp2);

                    RemoteService.getInstance().invokeByService(service, AppConstants.KEY_LOGIN, params,
                            callback, errorCallback);
                } catch (NoSuchAlgorithmException e) {
                    Toast.makeText(service, R.string.md5_error, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static void getDeviceRandCode(final BaseActivity mActivity, final BaseService service, final String did, final String certificate, final String key) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if ((jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE))) {
                        String randCode = jsonObject.getJSONObject(AppConstants.JSON_OBJ).getString(AppConstants.RAND_CODE);
                        getDeviceToken(mActivity, service, did, randCode, certificate, key);
                    } else {
                        if (mActivity != null) {
                            Toast.makeText(mActivity, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(service, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    if (mActivity != null) {
                        ToastUtils.showToast(mActivity, mActivity.getString(R.string.json_parse_exception));
                    } else {
                        Toast.makeText(service, service.getString(R.string.json_parse_exception), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mActivity != null) {
                    Toast.makeText(mActivity, R.string.smarthome_network_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(service, service.getString(R.string.smarthome_network_error), Toast.LENGTH_SHORT).show();
                }
            }
        };

        ArrayList<RequestParameter> params = new ArrayList<>();
        RequestParameter rp1 = new RequestParameter(AppConstants.CERTIFICATE, certificate);
        params.add(rp1);

        if (mActivity != null) {
            if (!NetUtils.isNetWorkConnected(mActivity)) {
                Toast.makeText(mActivity, R.string.network_error, Toast.LENGTH_SHORT).show();
            } else {
                RemoteService.getInstance().invoke(mActivity, AppConstants.KEY_CHECKCERTIFICATE, params,
                        callback, errorCallback);
            }
        } else {
            if (!NetUtils.isNetWorkConnected(mActivity)) {
                Toast.makeText(service, R.string.network_error, Toast.LENGTH_SHORT).show();
            } else {
                RemoteService.getInstance().invokeByService(service, AppConstants.KEY_CHECKCERTIFICATE, params,
                        callback, errorCallback);
            }
        }

    }

    private static void getDeviceToken(final BaseActivity mActivity, final BaseService service, final String did, String randCode, String certificate, String key) {
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
                                LocalUser currentUser = DataBaseEngine.getInstance().getCurrentUser();
                                currentUser.setUser_name(deviceObj.getString(AppConstants.LOCAL_USER_NAME));
                                DataBaseEngine.getInstance().updateUserInfo(currentUser.getUser_id(), currentUser);
                            }
                        });

                        AppConstantInfo.CLOUD_DEVICE.setToken(deviceObj.getString(AppConstants.TOKEN));

                        if (mLoginSuccessCallback != null) {
                            mLoginSuccessCallback.onLoginSuccess();
                        }

                    } else {
                        if (mActivity != null) {
                            Toast.makeText(mActivity, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(service, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    if (mActivity != null) {
                        ToastUtils.showToast(mActivity, mActivity.getString(R.string.json_parse_exception));
                    } else {
                        Toast.makeText(service, service.getString(R.string.json_parse_exception), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mActivity != null) {
                    Toast.makeText(mActivity, R.string.smarthome_network_error, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(service, service.getString(R.string.smarthome_network_error), Toast.LENGTH_SHORT).show();
                }
            }
        };

        try {
            ArrayList<RequestParameter> params = new ArrayList<>();
            RequestParameter rp1 = new RequestParameter(AppConstants.DEVICE_RAND_CODE, randCode);
            byte[] buffer = Base64.decode(key.getBytes(), Base64.URL_SAFE);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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

            if (mActivity != null) {
                if (!NetUtils.isNetWorkConnected(mActivity)) {
                    Toast.makeText(mActivity, R.string.network_error, Toast.LENGTH_SHORT).show();
                } else {
                    RemoteService.getInstance().invoke(mActivity, AppConstants.KEY_GETAUTHTOKEN, params,
                            callback, errorCallback);
                }
            } else {
                if (!NetUtils.isNetWorkConnected(service)) {
                    Toast.makeText(service, R.string.network_error, Toast.LENGTH_SHORT).show();
                } else {
                    RemoteService.getInstance().invokeByService(service, AppConstants.KEY_GETAUTHTOKEN, params,
                            callback, errorCallback);
                }
            }

        } catch (InvalidKeySpecException e) {
            if (mActivity != null) {
                Toast.makeText(mActivity, R.string.decompress_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(service, R.string.decompress_failed, Toast.LENGTH_SHORT).show();
            }

        } catch (NoSuchAlgorithmException e) {
            if (mActivity != null) {
                Toast.makeText(mActivity, R.string.decompress_failed, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(service, R.string.decompress_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void refreshCloudToken(final BaseActivity mActivity, final LoginSuccessCallback loginSuccessCallback) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.getString(AppConstants.REQUEST_CODE) != null &&
                            !jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE)) {
                        if (jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.CLOUD_TOKEN_EXPIRED)) {
                            Intent intent = new Intent(mActivity, LoginActivity.class);
                            mActivity.startActivity(intent);
                        } else {
                            Toast.makeText(mActivity, jsonObject.getString(AppConstants.JSON_MSG), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        JSONObject userInfo = (JSONObject) jsonObject.get(AppConstants.JSON_OBJ);
                        String token = userInfo.getString(AppConstants.TOKEN);
                        String refreshToken = userInfo.getString(AppConstants.REFRESH_TOKEN);

                        AppConstantInfo.CURRENT_USER.setRefresh_token(refreshToken);
                        AppConstantInfo.CURRENT_USER.setToken(token);

                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                DataBaseEngine.getInstance().changeCurrentUserInfo();
                                DataBaseEngine.getInstance().updateUserInfo(AppConstantInfo.CURRENT_USER.getUser_id(), AppConstantInfo.CURRENT_USER);
                            }
                        });
                        loginSuccessCallback.onLoginSuccess();
                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(mActivity, mActivity.getString(R.string.json_parse_exception));
                }
            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
            }
        };
        if (!NetUtils.isNetWorkConnected(mActivity)) {
            ToastUtils.showToast(mActivity, mActivity.getString(R.string.network_error));
        } else {
            ArrayList<RequestParameter> params = new ArrayList<>();
            params.add(new RequestParameter(AppConstants.REFRESH_TOKEN, AppConstantInfo.CURRENT_USER.getRefresh_token()));
            RemoteService.getInstance().invokeHeader(mActivity, AppConstants.REFRESH_CLOUD_TOKEN, "application/x-www-form-urlencoded", null, null, params,
                    callback, errorCallback);
        }
    }

    public static void cloudLogin(final BaseActivity mActivity, final LoginSuccessCallback loginSuccessCallback) {
        activity = mActivity;
        mLoginSuccessCallback = loginSuccessCallback;
        final String localUserName = AppConstantInfo.CURRENT_USER.getUser_id();
        final String localPasword = AppConstantInfo.CURRENT_USER.getUser_password();

        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.getString(AppConstants.REQUEST_CODE) != null &&
                            !jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE)) {
                        ToastUtils.showToast(mActivity, jsonObject.getString(AppConstants.JSON_MSG));
                    } else {
                        JSONObject userInfo = (JSONObject) jsonObject.get(AppConstants.JSON_OBJ);
                        String token = userInfo.getString(AppConstants.TOKEN);
                        String refreshToken = userInfo.getString(AppConstants.REFRESH_TOKEN);

                        final LocalUser localUser = new LocalUser(localUserName, localUserName, localPasword, AppConstantInfo.CURRENT_USER.getCloud_name(), 0, 0, token, refreshToken, true, "");
                        AppConstantInfo.CURRENT_USER = new LocalUser(localUserName, localUserName, localPasword, AppConstantInfo.CURRENT_USER.getCloud_name(), 0, 0, token, refreshToken, true, "");

                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                DataBaseEngine.getInstance().changeCurrentUserInfo();
                                DataBaseEngine.getInstance().updateUserInfo(localUserName, localUser);

                                List<CloudDevice> mDeviceList = DataBaseEngine.getInstance().getCloudDeviceList();
                                List<CloudDevice> removeList = new ArrayList<>();
                                for (CloudDevice device : mDeviceList) {
                                    if (device.getUser_id().equals(AppConstantInfo.CURRENT_USER.getUser_id())
                                            && (device.getCertificate() == null || device.getCertificate().equals(""))) {
                                        removeList.add(device);
                                    }
                                }

                                mDeviceList.removeAll(removeList);

                                if (mDeviceList != null && mDeviceList.size() != 0) {
                                    Message msg = mHandler.obtainMessage();
                                    msg.what = GET_DEVICE_LIST_SUCCESS;
                                    msg.obj = mDeviceList;
                                    mHandler.sendMessage(msg);
                                } else {
                                    mLoginSuccessCallback.onLoginSuccess();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(mActivity, mActivity.getString(R.string.json_parse_exception));
                }
            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
            }
        };
        if (!NetUtils.isNetWorkConnected(mActivity)) {
            ToastUtils.showToast(mActivity, mActivity.getString(R.string.network_error));
        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();

                ArrayList<RequestParameter> params = new ArrayList<>();
                RequestParameter rp1 = new RequestParameter(AppConstants.CLOUD_UID, localUserName);
                RequestParameter rp2 = new RequestParameter(AppConstants.PASSWORD, MD5Utils.getMD5Code(localPasword));
                RequestParameter rp3 = new RequestParameter(AppConstants.GUID, imei);
                params.add(rp1);
                params.add(rp2);
                params.add(rp3);

                RemoteService.getInstance().invokeHeader(mActivity, AppConstants.KEY_LOGINCLOUD, "application/x-www-form-urlencoded", null, null, params,
                        callback, errorCallback);
            } catch (NoSuchAlgorithmException e) {
                ToastUtils.showToast(mActivity, mActivity.getString(R.string.md5_error));
            }
        }
    }

    public static void getCertificates(final List<CloudDevice> list, final BaseActivity mActivity) {
        com.android.volley.Response.Listener<String> callback = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = JSON.parseObject(response);
                    if (jsonObject.getString(AppConstants.REQUEST_CODE) != null &&
                            !jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.SUCCESS_CODE)) {
                        if (jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.CLOUD_TOKEN_EXPIRED)) {
                            LoginUtil.refreshCloudToken(mActivity, new LoginSuccessCallback() {
                                @Override
                                public void onLoginSuccess() {
                                    getCertificates(list, mActivity);
                                }
                            });
                        } else if (jsonObject.getString(AppConstants.REQUEST_CODE).equals(AppConstants.INVALID_CLOUD_TOKEN)) {
                            deleteAllDevicesToLogin(activity);
                        } else {
                            ToastUtils.showToast(mActivity, jsonObject.getString(AppConstants.JSON_MSG));
                        }
                    } else {
                        final JSONArray array = jsonObject.getJSONArray(AppConstants.JSON_OBJ);
                        DefaultThreadPool.getInstance().execute(new Runnable() {
                            @Override
                            public void run() {
                                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                                for (int i = 0; i < array.size(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    CloudDevice localDevice = DataBaseEngine.getInstance().getCloudDevice(obj.getString(AppConstants.CLOUD_DID), AppConstantInfo.CURRENT_USER.getUser_id());
                                    localDevice.setCertificate(obj.getString(AppConstants.CERTIFICATE));
                                    localDevice.setKey(obj.getString(AppConstants.KEY));
                                    DataBaseEngine.getInstance().updateCloudDevice(localDevice.getDid(), localDevice);

                                    if (AppConstantInfo.CLOUD_DEVICE.getDid().equals(obj.getString(AppConstants.CLOUD_DID))) {
                                        AppConstantInfo.CLOUD_DEVICE.setCertificate(obj.getString(AppConstants.CERTIFICATE));
                                        AppConstantInfo.CLOUD_DEVICE.setKey(obj.getString(AppConstants.KEY));
                                    }
                                }

                                Message msg = mHandler.obtainMessage();
                                msg.what = GET_CERTIFICATE_SUCCESS;
                                mHandler.sendMessage(msg);
                            }
                        });

                    }
                } catch (JSONException e) {
                    ToastUtils.showToast(mActivity, mActivity.getString(R.string.json_parse_exception));
                }

            }
        };

        com.android.volley.Response.ErrorListener errorCallback = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showToast(mActivity, error.getMessage());
            }
        };

        StringBuilder tasks = new StringBuilder();
        if (list.size() == 1) {
            tasks.append(list.get(0).getDid());
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    tasks.append(list.get(i).getDid());
                    tasks.append(",");
                } else {
                    tasks.append(list.get(i).getDid());
                }
            }
        }

        ArrayList<RequestParameter> params = new ArrayList<>();
        RequestParameter rp1 = new RequestParameter(AppConstants.CLOUD_DIDS, tasks.toString());
        params.add(rp1);

        if (!NetUtils.isNetWorkConnected(mActivity)) {
            ToastUtils.showToast(mActivity, mActivity.getString(R.string.network_error));
        } else {
            RemoteService.getInstance().invokeHeader(mActivity, AppConstants.CERTIFICATES, "application/x-www-form-urlencoded", AppConstantInfo.CURRENT_USER.getToken(), null, params,
                    callback, errorCallback);
        }
    }

    //云账号修改密码，本地账号修改密码，以及删除账号导致token失效，需要用清除本地盒子列表。重新登录
    public static void deleteAllDevicesToLogin(Context context) {
        mContext = context;
        DefaultThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                DataBaseEngine.getInstance().deleteAllDevices();
                DataBaseEngine.getInstance().deleteCurrentLocalUser();

                Message msg = mHandler.obtainMessage();
                msg.what = DELETE_DEVICES_SUCCESS;
                mHandler.sendMessage(msg);
            }
        });
    }
}
