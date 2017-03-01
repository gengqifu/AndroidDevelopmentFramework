package com.example.examplelib.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class PostRequest extends Request<String> {

    private Map<String, String> mParams;
    private final Response.Listener<String> mListener;

    public PostRequest(String url, Map<String, String> params, Response.Listener<String> listener,
                       Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.mParams = params;
        this.mListener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError());
        }
    }

    @Override
    protected void deliverResponse(String response) {
        this.mListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.mParams;
    }
}
