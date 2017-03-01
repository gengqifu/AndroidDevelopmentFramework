package com.example.example.engine;

import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.example.example.db.CoCloudDatabaseLoader;
import com.example.examplelib.activity.BaseActivity;
import com.example.examplelib.cache.CacheManager;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.model.meta.Device;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExampleApplication extends MultiDexApplication {
    private static final String TAG = "smarthome";
    // If in developer mode.
    private static final boolean DEVELOPER_MODE = true;
    public static AndroidUpnpService upnpService;
    public static Device mCurrentDevice;

    private static com.example.example.engine.ExampleApplication application;

    /**
     * 记录所有活动的Activity
     */
    public static final List<BaseActivity> mActivities = new LinkedList<>();
    private Runnable uploadRunnable;
    private Runnable downloadRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        createAppFolder();
        CacheManager.getInstance().initCacheDir();
        CoCloudDatabaseLoader.init(this);
        if (DEVELOPER_MODE) {
            Logger.init(TAG)
                    .logLevel(LogLevel.FULL); //Use LogLevel.NONE for the release versions
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build());
        } else {
            Logger.init(TAG)
                    .logLevel(LogLevel.NONE);
        }

        if (AppConstantInfo.CURRENT_USER != null || AppConstantInfo.CLOUD_DEVICE != null) {
            MobclickAgent.openActivityDurationTrack(false);
        }

    }

    public static com.example.example.engine.ExampleApplication getApplication() {
        if (application == null) {
            application = new com.example.example.engine.ExampleApplication();
        }
        return application;
    }

    //创建应用的目录
    private void createAppFolder() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + AppConstants.LOCAL_DOWNlOAD);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    /**
     * 退出应用
     */
    public static void exitApp() {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
