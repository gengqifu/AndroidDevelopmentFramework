package com.example.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.example.R;
import com.example.example.activity.login.MainActivity;
import com.example.example.base.BaseFragment;
import com.example.example.engine.AppConstants;
import com.example.example.presenter.PagePresenter;
import com.example.example.view.PageView;

public class PageFragment extends BaseFragment {
    private static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private static PagePresenter mPagePresenter;
    private static final String TAG = "PageFragment";

    private boolean isThunder = false;
    private boolean isShowPhoneFile = false;
    private PageView pageView;

    public static PageFragment newInstance(int page, boolean isThunder, boolean isShowPhoneFile) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putBoolean(AppConstants.IS_THUNDER, isThunder);
        args.putBoolean(AppConstants.SHOW_PHONE_FILE, isShowPhoneFile);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        isThunder = getArguments().getBoolean(AppConstants.IS_THUNDER, false);
        isShowPhoneFile = getArguments().getBoolean(AppConstants.SHOW_PHONE_FILE, false);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_page, container, false);
        MainActivity activity = (MainActivity) getActivity();
        pageView = PageView.newInstance(activity);
        mPagePresenter = new PagePresenter((MainActivity) this.getActivity(), pageView);
        view = (RelativeLayout) mPagePresenter.showPage(inflater, mPage, isThunder, view, isShowPhoneFile);
        return view;
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void getMainData() {
        mPagePresenter.getMainData();
    }
}
