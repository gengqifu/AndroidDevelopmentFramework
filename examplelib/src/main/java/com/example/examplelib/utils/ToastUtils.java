package com.example.examplelib.utils;

import android.app.Activity;
import android.os.Looper;
import android.widget.Toast;


/**
 * 提供与Toast工具方法
 */
public class ToastUtils {


    private static void showToast(Activity ctx, String text, int duration) {
        if (ctx != null) {
            Toast.makeText(ctx, text, duration).show();
        }
    }

    /**
     * 提供Toast工具类，可以在非UI线程中运行；
     *
     * @param activity
     * @param msg
     */

    public static void showToast(final Activity activity, final String msg) {
        if (activity != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                showToast(activity, msg, Toast.LENGTH_SHORT);
            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(activity, msg, Toast.LENGTH_SHORT);
                    }
                });
            }
        }
    }

    public static void showToast(final Activity activity, final int id) {
        if (activity != null) {
            final String msg = activity.getResources().getString(id);
            if (Looper.myLooper() == Looper.getMainLooper()) {
                showToast(activity, msg, Toast.LENGTH_SHORT);
            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(activity, msg, Toast.LENGTH_SHORT);
                    }
                });
            }
        }
    }
}
