package com.example.example.activity.login;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.example.R;
import com.example.example.base.AppBaseActivity;
import com.example.example.presenter.LoginPresenter;
import com.example.example.view.LoginView;

public class LoginActivity extends AppBaseActivity {
    private static final String TAG = "LoginActivity";

    private LoginPresenter loginPresenter;

    @Override
    protected void initVariables() {
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        LoginView loginView = LoginView.newInstance(this);
        loginPresenter = new LoginPresenter(this, loginView);
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public Object getTag() {
        return TAG;
    }
}