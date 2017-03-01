package com.example.examplelib.activity;

import android.os.Bundle;

import com.example.examplelib.cache.IBaseManager;
import com.example.examplelib.net.RequestManager;
import com.example.examplelib.utils.Constants;
import com.zhy.autolayout.AutoLayoutActivity;

public abstract class BaseActivity extends AutoLayoutActivity implements IBaseManager {
    /**
     * 请求列表管理器
     */
    private RequestManager requestManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestManager = new RequestManager(this, getTag());

        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
        removeCachedFragment(savedInstanceState);
    }

    private void removeCachedFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove(Constants.FRAGMENTS_TAG);
        }
    }

    protected abstract void initVariables();

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void loadData();

    protected void onDestroy() {
        cancelVolley();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        cancelVolley();
        super.onPause();
    }

    public void cancelVolley() {
        /**
         * 在activity销毁的时候同时设置停止请求，停止线程请求回调
         */
        if (requestManager != null) {
            requestManager.cancelRequest();
        }

    }

    @Override
    public RequestManager getRequestManager() {
        return requestManager;
    }

    public abstract Object getTag();
}