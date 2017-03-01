package com.unicocloud.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CoCloudDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.unicocloud.smarthome.db");
        initLocalUserInfo(schema);
        initCloudDeviceInfo(schema);
        initDownloadInfo(schema);
        initUploadInfo(schema);
        initBackupPhotoInfo(schema);
        initRecentInfo(schema);
        initMessageInfo(schema);
        new DaoGenerator().generateAll(schema, args[0]);
    }


    public static void initLocalUserInfo(Schema schema) {
        Entity localUserInfo = schema.addEntity(DBFieldBeanConstant.LocalUserInfo.TABLE_NAME);
        localUserInfo.setTableName(DBFieldBeanConstant.LocalUserInfo.TABLE_NAME);
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.USER_ID).primaryKey();
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.USER_NAME);
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.USER_PASSWORD);
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.CLOUD_USER);
        localUserInfo.addIntProperty(DBFieldBeanConstant.LocalUserInfo.LOGIN_FLAG);
        localUserInfo.addIntProperty(DBFieldBeanConstant.LocalUserInfo.CLOUD_TYPE);
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.TOKEN);
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.REFRESH_TOKEN);
        localUserInfo.addBooleanProperty(DBFieldBeanConstant.LocalUserInfo.IS_CURRENT_ACCOUNT);
        localUserInfo.addStringProperty(DBFieldBeanConstant.LocalUserInfo.DEVICE_ID);
    }


    public static void initCloudDeviceInfo(Schema schema) {
        Entity cloudDeviceInfo = schema.addEntity(DBFieldBeanConstant.CloudDeviceInfo.TABLE_NAME);
        cloudDeviceInfo.setTableName(DBFieldBeanConstant.CloudDeviceInfo.TABLE_NAME);
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.DID).primaryKey();
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.USER_ID);
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.ADDRESS);
        cloudDeviceInfo.addIntProperty(DBFieldBeanConstant.CloudDeviceInfo.ONLINE);
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.CERTIFICATE);
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.KEY);
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.TOKEN);
        cloudDeviceInfo.addBooleanProperty(DBFieldBeanConstant.CloudDeviceInfo.IS_CURRENT_DEVICE);
        cloudDeviceInfo.addStringProperty(DBFieldBeanConstant.CloudDeviceInfo.DNAME);
        cloudDeviceInfo.addIntProperty(DBFieldBeanConstant.CloudDeviceInfo.IS_RELAY);
        cloudDeviceInfo.addIntProperty(DBFieldBeanConstant.CloudDeviceInfo.IS_REMOTECAN);
    }

    public static void initDownloadInfo(Schema schema) {
        Entity downloadInfo = schema.addEntity(DBFieldBeanConstant.DownloadInfo.TABLE_NAME);
        downloadInfo.setTableName(DBFieldBeanConstant.DownloadInfo.TABLE_NAME);
        downloadInfo.addLongProperty(DBFieldBeanConstant.DownloadInfo.ID).primaryKey().autoincrement();
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.USER_NAME);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.DID);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.URL);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.FILETYPE);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.TYPE);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.LOCALPATH);
        downloadInfo.addLongProperty(DBFieldBeanConstant.DownloadInfo.FILESIZE);
        downloadInfo.addLongProperty(DBFieldBeanConstant.DownloadInfo.STATE);
        downloadInfo.addLongProperty(DBFieldBeanConstant.DownloadInfo.COMPLETE);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.FILENAME);
        downloadInfo.addStringProperty(DBFieldBeanConstant.DownloadInfo.CREATETIME);
        downloadInfo.addIntProperty(DBFieldBeanConstant.DownloadInfo.ERRORMESSAGE);
    }

    public static void initUploadInfo(Schema schema) {
        Entity uploadInfo = schema.addEntity(DBFieldBeanConstant.UploadInfo.TABLE_NAME);
        uploadInfo.setTableName(DBFieldBeanConstant.UploadInfo.TABLE_NAME);
        uploadInfo.addLongProperty(DBFieldBeanConstant.UploadInfo.ID).primaryKey().autoincrement();
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.USER_NAME);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.DID);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.TYPE);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.URL);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.FILETYPE);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.LOCALPATH);
        uploadInfo.addLongProperty(DBFieldBeanConstant.UploadInfo.FILESIZE);
        uploadInfo.addLongProperty(DBFieldBeanConstant.UploadInfo.STATE);
        uploadInfo.addLongProperty(DBFieldBeanConstant.UploadInfo.COMPLETE);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.FILENAME);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.CREATETIME);
        uploadInfo.addStringProperty(DBFieldBeanConstant.UploadInfo.MD5);
        uploadInfo.addIntProperty(DBFieldBeanConstant.UploadInfo.ERRORMESSAGE);
    }

    public static void initBackupPhotoInfo(Schema schema) {
        Entity backupPhotoInfo = schema.addEntity(DBFieldBeanConstant.BackupPhotoInfo.TABLE_NAME);
        backupPhotoInfo.setTableName(DBFieldBeanConstant.BackupPhotoInfo.TABLE_NAME);
        backupPhotoInfo.addLongProperty(DBFieldBeanConstant.BackupPhotoInfo.ID).primaryKey().autoincrement();
        backupPhotoInfo.addStringProperty(DBFieldBeanConstant.BackupPhotoInfo.FILE_NAME);
        backupPhotoInfo.addStringProperty(DBFieldBeanConstant.BackupPhotoInfo.FILE_PATH);
        backupPhotoInfo.addLongProperty(DBFieldBeanConstant.BackupPhotoInfo.FILE_SIZE);
        backupPhotoInfo.addStringProperty(DBFieldBeanConstant.BackupPhotoInfo.MD5);
        backupPhotoInfo.addBooleanProperty(DBFieldBeanConstant.BackupPhotoInfo.STATE);
        backupPhotoInfo.addIntProperty(DBFieldBeanConstant.BackupPhotoInfo.CANCEL_STATE);
    }

    public static void initRecentInfo(Schema schema) {
        Entity recentInfo = schema.addEntity(DBFieldBeanConstant.RecentInfo.TABLE_NAME);
        recentInfo.setTableName(DBFieldBeanConstant.RecentInfo.TABLE_NAME);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.FILE_TYPE);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.USER);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.DID);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.FILE_PATH);
        recentInfo.addLongProperty(DBFieldBeanConstant.RecentInfo.TIME).primaryKey();
        recentInfo.addBooleanProperty(DBFieldBeanConstant.RecentInfo.IS_LOCAL);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.FILE_NAME);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.ID);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.CREATE_TIME);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.FILE_SIZE);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.MUSIC_ALBUM);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.MUSIC_SINGER);
        recentInfo.addStringProperty(DBFieldBeanConstant.RecentInfo.MEDIA_TIME);
        recentInfo.addIntProperty(DBFieldBeanConstant.RecentInfo.COLLECT_FLG);
        recentInfo.addIntProperty(DBFieldBeanConstant.RecentInfo.SHARE_FLG);
    }

    public static void initMessageInfo(Schema schema) {
        Entity messageInfo = schema.addEntity(DBFieldBeanConstant.MessageInfo.TABLE_NAME);
        messageInfo.setTableName(DBFieldBeanConstant.MessageInfo.TABLE_NAME);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.DID);
        messageInfo.addLongProperty(DBFieldBeanConstant.MessageInfo.ID).primaryKey().autoincrement();
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.IMGURL);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.MESSAGE);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.TIME);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.TITLE);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.TYPE);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.UID);
        messageInfo.addBooleanProperty(DBFieldBeanConstant.MessageInfo.DELETE_STATUS);
        messageInfo.addBooleanProperty(DBFieldBeanConstant.MessageInfo.READ_STATUS);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.VIDEO_FILEID);
        messageInfo.addStringProperty(DBFieldBeanConstant.MessageInfo.VIDEO_URL);
    }

}
