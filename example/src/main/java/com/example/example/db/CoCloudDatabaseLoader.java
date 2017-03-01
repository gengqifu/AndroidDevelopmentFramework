package com.example.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CoCloudDatabaseLoader {
    private static final String DATABASE_NAME = "cocloud-db";
    private static DaoSession daoSession;

    // 虚方法，可以无实体内容
    public static void init(Context context) {
        CoCloudDevOpenHelper helper = new CoCloudDevOpenHelper(context, DATABASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
