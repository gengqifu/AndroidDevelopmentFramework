package com.example.example.base;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.examplelib.cache.IBaseManager;
import com.example.examplelib.net.RequestManager;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseDialogFragment extends DialogFragment implements IBaseManager {
    /**
     * 请求列表管理器
     */
    private RequestManager requestManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requestManager = new RequestManager(getActivity(), getTAG());
        return initView(inflater, container);
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getTag());
    }

    @Override
    public void onPause() {
        /**
         * 在fragment停止的时候同时设置停止请求，停止线程请求回调
         */
        if (requestManager != null) {
            requestManager.cancelRequest();
        }
        MobclickAgent.onPageEnd(getTag());
        super.onPause();
    }

    @Override
    public void onDestroy() {
        /**
         * 在fragment销毁的时候同时设置停止请求，停止线程请求回调
         */
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
