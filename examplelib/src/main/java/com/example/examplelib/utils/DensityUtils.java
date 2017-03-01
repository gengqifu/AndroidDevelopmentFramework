package com.example.examplelib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

public class DensityUtils {
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /*
     * 设置屏幕不能锁屏
     * @param Activity activity
     * @return void
     */
    public static void setUnlocked(Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        win.setAttributes(winParams);
    }

    /*
     * 设置屏幕可以锁屏，当然默认是也是可以锁屏的
     * @param Activity activity
     * @return void
     */
    public static void setLocked(Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags &= (~WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                & ~WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                & ~WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                & ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        win.setAttributes(winParams);
    }
}
