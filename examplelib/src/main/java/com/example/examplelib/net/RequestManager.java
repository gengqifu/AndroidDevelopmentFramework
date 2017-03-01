package com.example.examplelib.net;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestManager {
    private RequestQueue mQueue;
    private Object tag;

    public RequestManager(final Context context, Object tag) {
        // 异步请求队列
        mQueue = Volley.newRequestQueue(context);
        this.tag = tag;
    }


    /**
     * <p>添加Request到列表</p>
     *
     * @param request Request 要添加到列表的请求
     * @return void
     */
    private void addRequest(final Request request) {
        request.setTag(tag);
        mQueue.add(request);
    }

    /**
     * <p>取消网络请求</p>.
     *
     * @param
     * @return void
     */
    public void cancelRequest() {
        if ((mQueue != null)) {
            mQueue.cancelAll(tag);
        }
    }

    /*
     * <p>创建无参数的String类型的网络请求</p>.
     *  @param urlData URLData url的相关信息
     *  @param callback Listener<String> 请求成功的回调
     *  @param errorCallback ErrorListener 请求失败的回调
     *  @return Request 网络请求
     */
    public Request createStringRequest(final URLData urlData,
                                       final Response.Listener<String> requestCallback,
                                       final Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                RequestType.parseRequestType(urlData.getNetType()),
                urlData.getUrl(), requestCallback, errorListener);
        addRequest(request);
        return request;
    }

    /*
     * <p>创建有参数的String类型的网络请求</p>.
     *  @param urlData URLData url的相关信息
     *  @param params List<RequestParameter> 请求的参数
     *  @param callback Listener<String> 请求成功的回调
     *  @param errorCallback ErrorListener 请求失败的回调
     *  @return Request 网络请求
     */
    public Request createStringRequest(final URLData urlData,
                                       final List<RequestParameter> params,
                                       final Response.Listener<String> requestCallback,
                                       final Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                RequestType.parseRequestType(urlData.getNetType()),
                urlData.getUrl(), requestCallback, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                for (int i = 0; i < params.size(); i++) {
                    RequestParameter rp = params.get(i);
                    map.put(rp.getName(), rp.getValue());
                }
                return map;
            }
        };

        addRequest(request);
        return request;
    }

    public Request createStringOneHeaderRequest(final URLData urlData,
                                                final String header,
                                                final Response.Listener<String> requestCallback,
                                                final Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                RequestType.parseRequestType(urlData.getNetType()),
                urlData.getUrl(), requestCallback, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", header);
                return headers;
            }
        };

        addRequest(request);
        return request;

    }

    public Request createFormRequest(final URLData urlData,
                                     final String filename,
                                     final Response.Listener<String> requestCallback,
                                     final Response.ErrorListener errorListener) {

        UploadFileRequest request = new UploadFileRequest(urlData.getUrl(),
                filename, requestCallback, errorListener);

        addRequest(request);
        return request;
    }

    public Request createStringHeaderRequest(final URLData urlData,
                                             final List<RequestParameter> params,
                                             final String header,
                                             final String authorization,
                                             final Response.Listener<String> requestCallback,
                                             final Response.ErrorListener errorListener) {
        StringRequest request;
        if (urlData.getNetType().equals(RequestType.POST)) {


            request = new StringRequest(
                    RequestType.parseRequestType(urlData.getNetType()),
                    urlData.getUrl(), requestCallback, errorListener) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < params.size(); i++) {
                        RequestParameter rp = params.get(i);
                        map.put(rp.getName(), rp.getValue());
                    }
                    return map;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", header);
                    headers.put("authorization", authorization);
                    return headers;
                }
            };
        } else {
            StringBuilder sb = new StringBuilder(urlData.getUrl());
            if (params != null) {
                sb.append("?");
                for (int i = 0; i < params.size(); i++) {
                    sb.append(params.get(i).getName());
                    sb.append("=");
                    sb.append(params.get(i).getValue() + "&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }

            request = new StringRequest(
                    RequestType.parseRequestType(urlData.getNetType()),
                    sb.toString(), requestCallback, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", header);
                    headers.put("authorization", authorization);
                    return headers;
                }
            };
        }
        addRequest(request);
        return request;
    }


    /*
     * <p>创建要参数的JSON类型的网络请求</p>.
     *  @param urlData URLData url的相关信息
     *  @param params JSONObject 请求的参数
     *  @param callback Listener<String> 请求成功的回调
     *  @param errorCallback ErrorListener 请求失败的回调
     *  @return Request 网络请求
     */
    public Request createJsonRequest(final URLData urlData,
                                     JSONObject params, final Response.Listener<JSONObject> requestCallback,
                                     final Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(
                RequestType.parseRequestType(urlData.getNetType()),
                urlData.getUrl(), params, requestCallback, errorListener);
        addRequest(request);
        return request;
    }
}
