package com.example.examplelib.net;

import com.android.volley.Request;

class RequestType {
    private static final String GET = "get";
    public static final String POST = "post";

    public static int parseRequestType(String netType) {
        int type = Request.Method.GET;
        if (netType.equalsIgnoreCase(GET)) {
            type = Request.Method.GET;
        } else if (netType.equalsIgnoreCase(POST)) {
            type = Request.Method.POST;
        }
        return type;
    }
}
