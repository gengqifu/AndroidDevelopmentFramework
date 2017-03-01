package com.example.example.engine;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.example.example.base.BaseService;
import com.example.example.mockdata.MockService;
import com.example.examplelib.activity.BaseActivity;
import com.example.examplelib.net.RequestParameter;
import com.example.examplelib.net.Response;
import com.example.examplelib.net.URLData;
import com.example.examplelib.net.UrlConfigManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RemoteService {
    private static RemoteService service;

    public String mTransmit = "";

    private RemoteService() {

    }

    public void setTransmit(String port) {
        mTransmit = port;
    }

    public boolean isTransmit(URLData urlData) {
        return mTransmit != null && !mTransmit.equals("") && urlData.getUrl().contains(NetUrl.BASE_URL);
    }

    public URLData getTransmitUrl(URLData urlData) {
        URLData transmitUrl = new URLData();
        if (isTransmit(urlData)) {
            transmitUrl.setUrl(urlData.getUrl().replace(NetUrl.BASE_URL, mTransmit));
            transmitUrl.setNetType(urlData.getNetType());
            transmitUrl.setMockClass(urlData.getMockClass());
            transmitUrl.setKey(urlData.getKey());
            transmitUrl.setExpires(urlData.getExpires());
        }

        return transmitUrl;
    }

    public static synchronized RemoteService getInstance() {
        if (RemoteService.service == null) {
            RemoteService.service = new RemoteService();
        }
        return RemoteService.service;
    }

    /*
     * <p>发起网络请求：如果有真正的服务端API，则调用；否则调用MockClass</p>.
     *  @param activity BaseActivity 请求所处的上下文Activity
     *  @param apiKey String api的关键字
     *  @param params List<RequestParameter> 请求的参数
     *  @param callback Listener<String> 请求成功的回调
     *  @param errorCallback ErrorListener 请求失败的回调
     *  @return void
     */
    public void invoke(final BaseActivity activity,
                       final String apiKey,
                       final List<RequestParameter> params,
                       final com.android.volley.Response.Listener<String> callback,
                       final com.android.volley.Response.ErrorListener errorCallback) {

        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        if ((urlData != null ? urlData.getMockClass() : null) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (activity != null) {
                if (isTransmit(urlData)) {
                    activity.getRequestManager().createStringRequest(
                            getTransmitUrl(urlData), params, callback, errorCallback);
                } else {
                    activity.getRequestManager().createStringRequest(
                            urlData, params, callback, errorCallback);
                }
            }
        }
    }

    public void invoke(final BaseActivity activity,
                       final String apiKey,
                       final String urlParams,
                       final com.android.volley.Response.Listener<String> callback,
                       final com.android.volley.Response.ErrorListener errorCallback) {

        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        assert urlData != null;
        URLData urlDataParams = new URLData();
        if (urlParams != null && !urlParams.equals("")) {
            urlDataParams.setExpires(urlData.getExpires());
            urlDataParams.setKey(urlData.getKey());
            urlDataParams.setMockClass(urlData.getMockClass());
            urlDataParams.setNetType(urlData.getNetType());
            urlDataParams.setUrl(urlData.getUrl() + urlParams);
        }
        if ((urlData.getMockClass()) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (urlParams != null && !urlParams.equals("")) {
                activity.getRequestManager().createStringRequest(
                        urlDataParams, callback, errorCallback);
            } else {
                if (activity != null) {
                    if (isTransmit(urlData)) {
                        activity.getRequestManager().createStringRequest(
                                getTransmitUrl(urlData), callback, errorCallback);
                    } else {
                        activity.getRequestManager().createStringRequest(
                                urlData, callback, errorCallback);
                    }
                }
            }
        }
    }

    public void invokeUrl(final BaseActivity activity,
                          final String apiKey,
                          final String urlParams,
                          final List<RequestParameter> params,
                          final com.android.volley.Response.Listener<String> callback,
                          final com.android.volley.Response.ErrorListener errorCallback) {

        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        assert urlData != null;
        URLData urlDataParams = new URLData();
        if (urlParams != null && !urlParams.equals("")) {
            urlDataParams.setExpires(urlData.getExpires());
            urlDataParams.setKey(urlData.getKey());
            urlDataParams.setMockClass(urlData.getMockClass());
            urlDataParams.setNetType(urlData.getNetType());
            urlDataParams.setUrl(urlData.getUrl() + urlParams);
        }
        if ((urlData.getMockClass()) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (urlParams != null && !urlParams.equals("")) {
                activity.getRequestManager().createStringRequest(
                        urlDataParams, params, callback, errorCallback);
            } else {
                if (activity != null) {
                    if (isTransmit(urlData)) {
                        activity.getRequestManager().createStringRequest(
                                getTransmitUrl(urlData), params, callback, errorCallback);
                    } else {
                        activity.getRequestManager().createStringRequest(
                                urlData, params, callback, errorCallback);
                    }
                }
            }
        }
    }

    public void invokeByService(final BaseService service,
                                final String apiKey,
                                final List<RequestParameter> params,
                                final com.android.volley.Response.Listener<String> callback,
                                final com.android.volley.Response.ErrorListener errorCallback) {
        final URLData urlData = UrlConfigManager.findURL(service, apiKey);
        if ((urlData != null ? urlData.getMockClass() : null) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (isTransmit(urlData)) {
                service.getRequestManager().createStringRequest(
                        getTransmitUrl(urlData), params, callback, errorCallback);
            } else {
                service.getRequestManager().createStringRequest(
                        urlData, params, callback, errorCallback);
            }

        }
    }

    public void invokeForm(final BaseActivity activity,
                           final String apiKey,
                           final String filename,
                           final String urlParams,
                           final com.android.volley.Response.Listener<String> callback,
                           final com.android.volley.Response.ErrorListener errorCallback) {

        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        assert urlData != null;
        URLData urlDataParams = new URLData();
        if (urlParams != null && !urlParams.equals("")) {
            urlDataParams.setExpires(urlData.getExpires());
            urlDataParams.setKey(urlData.getKey());
            urlDataParams.setMockClass(urlData.getMockClass());
            urlDataParams.setNetType(urlData.getNetType());
            urlDataParams.setUrl(urlData.getUrl() + urlParams);
        }
        if ((urlData.getMockClass()) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (urlParams != null && !urlParams.equals("")) {
                activity.getRequestManager().createFormRequest(
                        urlDataParams, filename, callback, errorCallback);
            } else {
                if (activity != null) {
                    if (isTransmit(urlData)) {
                        activity.getRequestManager().createFormRequest(
                                getTransmitUrl(urlData), filename, callback, errorCallback);
                    } else {
                        activity.getRequestManager().createFormRequest(
                                urlData, filename, callback, errorCallback);
                    }
                }
            }
        }
    }

    public void invokeHeader(final BaseActivity activity,
                             final String apiKey,
                             final String header,
                             final String authorization,
                             final String urlParams,
                             final List<RequestParameter> params,
                             final com.android.volley.Response.Listener<String> callback,
                             final com.android.volley.Response.ErrorListener errorCallback) {

        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        assert urlData != null;
        URLData urlDataParams = new URLData();
        if (urlParams != null && !urlParams.equals("")) {
            urlDataParams.setExpires(urlData.getExpires());
            urlDataParams.setKey(urlData.getKey());
            urlDataParams.setMockClass(urlData.getMockClass());
            urlDataParams.setNetType(urlData.getNetType());
            urlDataParams.setUrl(urlData.getUrl() + urlParams);
        }
        if ((urlData != null ? urlData.getMockClass() : null) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (urlParams != null && !urlParams.equals("")) {
                activity.getRequestManager().createStringHeaderRequest(
                        urlDataParams, params, header, authorization, callback, errorCallback);
            } else {
                if (activity != null) {
                    if (isTransmit(urlData)) {
                        activity.getRequestManager().createStringHeaderRequest(
                                getTransmitUrl(urlData), params, header, authorization, callback, errorCallback);
                    } else {
                        activity.getRequestManager().createStringHeaderRequest(
                                urlData, params, header, authorization, callback, errorCallback);
                    }
                }
            }

        }
    }

    /*
     * <p>发起网络请求：如果有真正的服务端API，则调用；否则调用MockClass</p>.
     *  @param activity BaseActivity 请求所处的上下文Activity
     *  @param apiKey JSONObject api的关键字
     *  @param params List<RequestParameter> 请求的参数
     *  @param callback Listener<JSONObject> 请求成功的回调
     *  @param errorCallback ErrorListener 请求失败的回调
     *  @return void
     */
    public void invokeJson(final BaseActivity activity,
                           final String apiKey,
                           final JSONObject params,
                           final com.android.volley.Response.Listener<JSONObject> callback,
                           final com.android.volley.Response.ErrorListener errorCallback) {
        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        if ((urlData != null ? urlData.getMockClass() : null) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    try {
                        callback.onResponse(new JSONObject(strResponse));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (activity != null) {
                if (isTransmit(urlData)) {
                    activity.getRequestManager().createJsonRequest(
                            getTransmitUrl(urlData), params, callback, errorCallback);
                } else {
                    activity.getRequestManager().createJsonRequest(
                            urlData, params, callback, errorCallback);
                }
            }
        }
    }

    /*
 * <p>发起网络请求：如果有真正的服务端API，则调用；否则调用MockClass</p>.
 *  @param activity BaseActivity 请求所处的上下文Activity
 *  @param apiKey String api的关键字
 *  @param params List<RequestParameter> 请求的参数
 *  @param callback Listener<String> 请求成功的回调
 *  @param errorCallback ErrorListener 请求失败的回调
 *  @param timeout 设置超时时间
 *  @return void
 */
    public void invoke(final BaseActivity activity,
                       final String apiKey,
                       final List<RequestParameter> params,
                       final com.android.volley.Response.Listener<String> callback,
                       final com.android.volley.Response.ErrorListener errorCallback,
                       int timeout) {

        final URLData urlData = UrlConfigManager.findURL(activity, apiKey);
        if ((urlData != null ? urlData.getMockClass() : null) != null) {
            try {
                MockService mockService = (MockService) Class.forName(
                        urlData.getMockClass()).newInstance();
                String strResponse = mockService.getJsonData();

                final Response responseInJson = JSON.parseObject(strResponse,
                        Response.class);
                if (responseInJson.hasError()) {
                    errorCallback.onErrorResponse(new VolleyError(strResponse));
                } else {
                    callback.onResponse(strResponse);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            if (activity != null) {
                if (isTransmit(urlData)) {
                    activity.getRequestManager().createStringRequest(
                            getTransmitUrl(urlData), params, callback, errorCallback)
                            .setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                } else {
                    activity.getRequestManager().createStringRequest(
                            urlData, params, callback, errorCallback)
                            .setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                }
            }
        }
    }
}