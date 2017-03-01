package com.example.example.base;

import android.app.Service;

import com.example.examplelib.cache.IBaseManager;
import com.example.examplelib.net.RequestManager;

public abstract class BaseService extends Service implements IBaseManager {
    private RequestManager requestManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        requestManager = new RequestManager(this, getTAG());
    }

    @Override
    public void onDestroy() {
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        super.onDestroy();
    }

    protected abstract String getTAG();

    @Override
    public RequestManager getRequestManager() {
        return requestManager;
    }
}
