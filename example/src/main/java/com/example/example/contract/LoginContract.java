package com.example.example.contract;

import com.example.example.view.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        void cancelButtonSetting();
    }

    interface Presenter {
        void login(String userName, String password);

        void getDevice(String userName, String password);
    }
}