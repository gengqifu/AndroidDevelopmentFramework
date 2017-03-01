package com.example.example.presenter;

import android.content.IntentFilter;
import android.view.LayoutInflater;

import com.example.example.activity.login.MainActivity;
import com.example.example.contract.PageContract;
import com.example.example.engine.AppConstants;

import java.lang.ref.SoftReference;

public class PagePresenter implements PageContract.Presenter {
    private static final int GET_TOKEN = 0;

    private static final String SHORTCUT = "1";
    private static final String SHORTCUT_AND_OTHER = "0";

    private static final int RECENT = 1;
    private final PageContract.View mPageView;

    private final SoftReference<MainActivity> mActivity;
    private IntentFilter intentFilter;
    private int currentPage;

    public PagePresenter(MainActivity activity, PageContract.View tasksView) {
        mActivity = new SoftReference<>(activity);
        mPageView = tasksView;
        mPageView.setPresenter(this);
    }

    @Override
    public android.view.View showPage(LayoutInflater inflater, int page, boolean isThunder, android.view.ViewGroup view, boolean isShowPhoneFile) {
        if (page == 1) {
            currentPage = page;
            intentFilter = new IntentFilter();
            intentFilter.addAction(AppConstants.REFRESH_ADAPTER_ADD);
            intentFilter.addAction(AppConstants.REFRESH_ADAPTER_REDUCE);
            intentFilter.addAction(AppConstants.ACTION_RECENT);
            intentFilter.addAction(AppConstants.ACTION_RECENT_DELETE);
        }
        return mPageView.showPage(inflater, page, isThunder, view, isShowPhoneFile);
    }

    @Override
    public void getMainData() {
    }

}
