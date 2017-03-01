package com.example.example.activity.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.example.R;
import com.example.example.base.AppBaseActivity;
import com.example.example.engine.AppConstantInfo;
import com.example.example.engine.AppConstants;
import com.example.example.presenter.MainPresenter;
import com.example.example.view.MainView;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends AppBaseActivity {
    private static final String TAG = "MainActivity";
    private static final int FILE_ALL_INSIDE = 1;

    private MainPresenter mainPresenter;
    private MainView mainView;
    private String token = "";
    private boolean isCloud = false;
    private boolean isThunder = false;
    private String device = "";

    private String isShowPhoneFile = "";

    private RefreshBoxNameReceive receive;

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_main);
        if (AppConstantInfo.CURRENT_USER != null || AppConstantInfo.CLOUD_DEVICE != null) {
            MobclickAgent.openActivityDurationTrack(false);
        }
        mainView = MainView.newInstance(this);
        mainPresenter = new MainPresenter(this, mainView);
        mainView.setToken(token, device, isCloud, isThunder, AppConstants.SHOW_PHONE_FILE.equals(isShowPhoneFile));
        receive = new RefreshBoxNameReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstants.ACTION_NICK_NAME_SERVICE);
        this.registerReceiver(receive, filter);
    }

    @Override
    protected void initVariables() {
        token = getIntent().getStringExtra(AppConstants.TOKEN);
        isCloud = getIntent().getBooleanExtra(AppConstants.IS_CLOUD_ACCOUNT, false);
        isThunder = getIntent().getBooleanExtra(AppConstants.IS_THUNDER, false);
        device = getIntent().getStringExtra(AppConstants.SELECTED_DEVICE);
        isShowPhoneFile = getIntent().getStringExtra(AppConstants.SHOW_PHONE_FILE);
        com.example.example.engine.ExampleApplication.mActivities.add(this);
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            mainPresenter.handleActivityResult(requestCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(receive);
        super.onDestroy();

    }

    public void setBack() {
        mainPresenter.setBack();
    }

    public Toolbar getToolbar() {
        return mainPresenter.getToolbar();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            mainView.onBackPressed();
        }
    }

    public class RefreshBoxNameReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String name = intent.getStringExtra(AppConstants.NAME);
            if (name != null && name.length() > 0) {
                mainView.refreshBoxName(name);
            }
        }
    }
}
