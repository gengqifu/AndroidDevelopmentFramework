package com.example.example.contract;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.example.example.view.BaseView;

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void setBack();

        Toolbar getToolbar();

        void setBoxName(String boxName);

        void showHomePage();

        void refreshBoxName(String name);
    }

    interface Presenter {
        void handleActivityResult(int requestCode, Intent intent);

        void handleShortcut(Intent intent);

        void setBack();

        Toolbar getToolbar();

        void getBoxName();
    }
}
