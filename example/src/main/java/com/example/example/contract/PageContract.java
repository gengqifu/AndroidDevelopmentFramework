package com.example.example.contract;

import android.view.LayoutInflater;

import com.example.example.presenter.BasePresenter;
import com.example.example.view.BaseView;

public interface PageContract {
    interface View extends BaseView<Presenter> {
        android.view.View showPage(LayoutInflater inflater, int page, boolean isThunder, android.view.ViewGroup view, boolean isShowPhoneFile);
    }

    interface Presenter extends BasePresenter {
        android.view.View showPage(LayoutInflater inflater, int page, boolean isThunder, android.view.ViewGroup view, boolean isShowPhoneFile);

        void getMainData();
    }
}
