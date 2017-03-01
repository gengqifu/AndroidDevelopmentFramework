package com.example.examplelib.net;

public interface RequestCallback {
    void onSuccess(String content);

    void onFail(String errorMessage);

    void onCookieExpired();
}
