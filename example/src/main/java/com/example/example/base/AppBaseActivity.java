package com.example.example.base;

import android.os.Bundle;

import com.example.examplelib.activity.BaseActivity;
import com.umeng.analytics.MobclickAgent;

public abstract class AppBaseActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.example.engine.ExampleApplication.mActivities.add(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart((String) getTag()); //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);          //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd((String) getTag()); // （仅有Activity的应用中SDK自动调用，不需要单独写）保证 onPageEnd 在onPause 之前调用,因为
        // onPause 中会保存信息。"SplashScreen"为页面名称，可自定义
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        com.example.example.engine.ExampleApplication.mActivities.remove(this);
    }
}
