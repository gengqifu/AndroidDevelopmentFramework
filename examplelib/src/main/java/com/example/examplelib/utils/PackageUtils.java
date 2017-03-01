package com.example.examplelib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageUtils {

    public static PackageInfo getPackageInfo(Context context) {
        if (null == context) {
            return null;
        }
        String packageName = context.getPackageName();
        if (StringUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        PackageInfo info;
        PackageManager manager = context.getPackageManager();
        // 根据packageName获取packageInfo
        try {
            info = manager.getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return info;
    }

    public static int getVersionCode(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            return info.versionCode;
        } else {
            return -1;
        }
    }

    public static String getVersionName(Context context) {
        PackageInfo info = getPackageInfo(context);
        if (info != null) {
            return info.versionName;
        } else {
            return "";
        }
    }

}
