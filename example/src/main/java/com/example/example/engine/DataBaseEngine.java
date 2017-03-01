package com.example.example.engine;

import com.example.example.db.BackupPhotoInfo;
import com.example.example.db.BackupPhotoInfoDao;
import com.example.example.db.CloudDevice;
import com.example.example.db.CloudDeviceDao;
import com.example.example.db.CoCloudDatabaseLoader;
import com.example.example.db.DownloadInfo;
import com.example.example.db.DownloadInfoDao;
import com.example.example.db.LocalUser;
import com.example.example.db.LocalUserDao;
import com.example.example.db.MessageInfo;
import com.example.example.db.MessageInfoDao;
import com.example.example.db.RecentInfo;
import com.example.example.db.RecentInfoDao;
import com.example.example.db.UploadInfo;
import com.example.example.db.UploadInfoDao;
import com.example.example.utils.DESUtils;
import com.example.example.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class DataBaseEngine {

    private static DataBaseEngine dataBaseEngine;

    private DataBaseEngine() {

    }

    public static synchronized DataBaseEngine getInstance() {
        if (dataBaseEngine == null) {
            synchronized (DataBaseEngine.class) {
                if (dataBaseEngine == null) {
                    dataBaseEngine = new DataBaseEngine();
                }
            }

        }
        return dataBaseEngine;
    }

    public void deleteCurrentLocalUser() {
        LocalUser localUser = getCurrentUser();
        if (localUser != null) {
            CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().delete(localUser);
        }
    }

    public void updateCurrentCloudType(int type) {
        LocalUser currentUser = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique();

        if (currentUser != null) {
            currentUser.setCloud_type(type);
            CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().update(currentUser);
        }
    }

    public void updateCurrentUserPassword(String paw) {
        LocalUser currentUser = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique();

        if (currentUser != null) {

            try {
                currentUser.setUser_password(DESUtils.encrypt(paw));
                CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().update(currentUser);
            } catch (Exception e) {
            }
        }
    }

    public LocalUser getCurrentUser() {
        LocalUser user = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique();
        if (user != null) {
            try {
                user = new LocalUser(user.getUser_id(), DESUtils.decrypt(user.getUser_name()), DESUtils.
                        decrypt(user.getUser_password()), user.getCloud_name(), user.getLogin_flg(),
                        user.getCloud_type(), user.getToken(), user.getRefresh_token(), user.getCurrent_account()
                        , user.getDid());
            } catch (Exception e) {
                return null;
            }

        }
        return user;
    }

    private void insertUserInfo(LocalUser localUser) {
        CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().insert(localUser);
    }

    public void changeCurrentUserInfo() {
        LocalUser currentUser = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique();

        if (currentUser != null) {
            currentUser.setCurrent_account(false);
            CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().update(currentUser);
        }
    }

    public String getCurrentUserToken() {
        return CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique().getToken();
    }

    public String getCloudUserRefreshToken() {
        return CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique().getRefresh_token();
    }

    public String getCloudUserId() {
        return CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique().getUser_id();
    }

    public void updateUserInfo(String id, LocalUser userInfo) {
        LocalUser user = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.User_id.eq(id)).unique();

        try {
            userInfo.setUser_name(DESUtils.encrypt(userInfo.getUser_name()));
            userInfo.setUser_password(DESUtils.encrypt(userInfo.getUser_password()));
            if (user != null) {
                CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().update(userInfo);
            } else {
                insertUserInfo(userInfo);
            }
        } catch (Exception e) {
        }

    }

    public void updateCurrentUserToken(String token) {
        LocalUser currentUser = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique();

        if (currentUser != null) {
            currentUser.setToken(token);
            CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().update(currentUser);
        }
    }

    public String getCurrentUserPassword() {
        LocalUser currentUser = CoCloudDatabaseLoader.getDaoSession().getLocalUserDao().queryBuilder()
                .where(LocalUserDao.Properties.Current_account.eq(true)).unique();

        if (currentUser != null) {
            try {
                return DESUtils.decrypt(currentUser.getUser_password());
            } catch (Exception e) {
            }
            return "";
        } else {
            return "";
        }
    }

    public void updateCloudDevice(String did, CloudDevice device) {
        CloudDevice cloudDevice = CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder()
                .where(CloudDeviceDao.Properties.Did.eq(did)).unique();

        if (cloudDevice != null) {
            CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().update(device);
        } else {
            CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().insert(device);
        }
    }

    public String getCloudDeviceCertificate(String did) {
        CloudDevice cloudDevice = CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder()
                .where(CloudDeviceDao.Properties.Did.eq(did)).unique();
        if (cloudDevice != null) {
            return cloudDevice.getCertificate();
        } else {
            return "";
        }
    }

    public String getCurrentCloudDeviceCertificate() {
        CloudDevice cloudDevice = CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder()
                .where(CloudDeviceDao.Properties.Current_device.eq(true)).unique();
        if (cloudDevice != null) {
            return cloudDevice.getCertificate();
        } else {
            return "";
        }
    }

    public void changeCurrentDevice() {
        CloudDevice cloudDevice = CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder()
                .where(CloudDeviceDao.Properties.Current_device.eq(true)).unique();

        if (cloudDevice != null) {
            cloudDevice.setCurrent_device(false);
            CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().update(cloudDevice);
        }
    }

    public CloudDevice getCloudDevice(String did, String userId) {
        return CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder()
                .where(CloudDeviceDao.Properties.Did.eq(did), CloudDeviceDao.Properties.User_id.eq(userId)).unique();
    }

    public CloudDevice getCurrentCloudDevice() {
        return CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder()
                .where(CloudDeviceDao.Properties.Current_device.eq(true)).unique();
    }

    public void insertDownloadInfo(DownloadInfo downloadInfo) {
        CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().insert(downloadInfo);
    }

    public void insertDownloadInfoList(List<DownloadInfo> infos) {
        for (DownloadInfo downloadInfo : infos) {
            DownloadInfo info;
            if (Utils.isCloudLogin()) {
                info = CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.FileName.eq(downloadInfo.getFileName()))
                        .where(DownloadInfoDao.Properties.LocalPath.eq(downloadInfo.getLocalPath()))
                        .where(DownloadInfoDao.Properties.Url.eq(downloadInfo.getUrl()))
                        .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).unique();
            } else {
                info = CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.FileName.eq(downloadInfo.getFileName()))
                        .where(DownloadInfoDao.Properties.LocalPath.eq(downloadInfo.getLocalPath()))
                        .where(DownloadInfoDao.Properties.Url.eq(downloadInfo.getUrl())).where(DownloadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                        .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).unique();
            }

            if (info == null) {
                insertDownloadInfo(downloadInfo);
            } else {
                if (info.getState() == AppConstants.FINISH) {
                    if (downloadInfo.getType().equals(AppConstants.COVER)) {
                        insertDownloadInfo(downloadInfo);
                    }
                }
            }
        }
    }

    public DownloadInfo queryOneDownloadInfo(long id) {
        if (Utils.isCloudLogin()) {
            return CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.Id.eq(id))
                    .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).unique();
        } else {
            return CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.Id.eq(id)).where(DownloadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                    .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).unique();
        }

    }

    public void updateDownloadInfo(long id, DownloadInfo downloadInfo) {
        DownloadInfo download;
        if (Utils.isCloudLogin()) {
            download = CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder()
                    .where(DownloadInfoDao.Properties.Id.eq(id))
                    .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).unique();
        } else {
            download = CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder()
                    .where(DownloadInfoDao.Properties.Id.eq(id)).where(DownloadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                    .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).unique();
        }


        if (download != null) {
            CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().update(downloadInfo);
        } else {
            insertDownloadInfo(downloadInfo);
        }
    }

    public List<DownloadInfo> queryDownloadInfo(int state) {
        if (Utils.isCloudLogin()) {
            return CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.State.eq(state))
                    .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).list();
        } else {
            return CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.State.eq(state)).where(DownloadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                    .where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).list();
        }

    }

    public List<DownloadInfo> queryAllDownloadInfo() {
        List<DownloadInfo> list = new ArrayList<>();
        if (Utils.isCloudLogin()) {
            if (AppConstantInfo.CLOUD_DEVICE != null) {
                list = CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).list();
            }

        } else {
            if (AppConstantInfo.CURRENT_USER != null) {
                list = CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().queryBuilder().where(DownloadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name())).where(DownloadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).list();
            }

        }
        return list;

    }

    public void deleteAllDownInfo() {
        CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().deleteAll();
    }

    public void deleteCurrentDownloadInfo(Long id) {
        CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().deleteByKey(id);
    }

    public void insertUploadInfo(UploadInfo uploadInfo) {
        CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().insert(uploadInfo);
    }

    public void insertUploadInfoList(List<UploadInfo> list) {
        for (UploadInfo uploadInfo : list) {
            UploadInfo info;
            if (Utils.isCloudLogin()) {
                info = CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder().where(UploadInfoDao.Properties.FileName.eq(uploadInfo.getFileName()))
                        .where(UploadInfoDao.Properties.LocalPath.eq(uploadInfo.getLocalPath()))
                        .where(UploadInfoDao.Properties.Url.eq(uploadInfo.getUrl()))
                        .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).unique();
            } else {
                info = CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder().where(UploadInfoDao.Properties.FileName.eq(uploadInfo.getFileName()))
                        .where(UploadInfoDao.Properties.LocalPath.eq(uploadInfo.getLocalPath()))
                        .where(UploadInfoDao.Properties.Url.eq(uploadInfo.getUrl()))
                        .where(UploadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                        .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).unique();
            }

            if (info == null) {
                insertUploadInfo(uploadInfo);
            } else {
                if (info.getState() != AppConstants.FINISH) {
                    if (uploadInfo.getType().equals(AppConstants.COVER)) {
                        info.setComplete(0L);
                        updateUploadInfo(info.getId(), info);
                    }
                } else if (uploadInfo.getType().equals(AppConstants.COVER)) {
                    insertUploadInfo(uploadInfo);
                }

            }
        }
    }

    public List<UploadInfo> queryAllUploadInfo() {
        List<UploadInfo> list = new ArrayList<>();
        if (Utils.isCloudLogin()) {

            if (AppConstantInfo.CLOUD_DEVICE != null) {
                list = CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder()
                        .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).list();
            }

        } else {
            if (AppConstantInfo.CURRENT_USER != null) {
                list = CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder().where(UploadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                        .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).list();
            }

        }
        return list;

    }

    public UploadInfo queryOneUploadInfo(long id) {
        if (Utils.isCloudLogin()) {
            return CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder().where(UploadInfoDao.Properties.Id.eq(id))
                    .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).unique();
        } else {
            return CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder().where(UploadInfoDao.Properties.Id.eq(id))
                    .where(UploadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                    .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).unique();
        }

    }

    public List<UploadInfo> queryUploadInfo(int state) {
        if (Utils.isCloudLogin()) {
            return CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder()
                    .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).list();
        } else {
            return CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder().where(UploadInfoDao.Properties.State.eq(state)).where(UploadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                    .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).list();
        }

    }

    public void updateUploadInfo(long id, UploadInfo uploadInfo) {
        UploadInfo download;
        if (Utils.isCloudLogin()) {
            download = CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder()
                    .where(UploadInfoDao.Properties.Id.eq(id))
                    .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CLOUD_DEVICE.getDid())).unique();
        } else {
            download = CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().queryBuilder()
                    .where(UploadInfoDao.Properties.Id.eq(id))
                    .where(UploadInfoDao.Properties.UserName.eq(AppConstantInfo.CURRENT_USER.getUser_name()))
                    .where(UploadInfoDao.Properties.Did.eq(AppConstantInfo.CURRENT_USER.getDid())).unique();
        }


        if (download != null) {
            CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().update(uploadInfo);
        } else {
            insertUploadInfo(uploadInfo);
        }
    }

    public void deleteCurrentUploadInfo(Long id) {
        CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().deleteByKey(id);
    }

    public void deleteListUploadInfo(List<Long> ids) {
        CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().deleteByKeyInTx(ids);
    }

    public void deleteListDownloadloadInfo(List<Long> ids) {
        CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().deleteByKeyInTx(ids);
    }

    public void updateDownloadInfoList(List<DownloadInfo> infos) {
        CoCloudDatabaseLoader.getDaoSession().getDownloadInfoDao().updateInTx(infos);
    }

    public void updateUploadInfoList(List<UploadInfo> infos) {
        CoCloudDatabaseLoader.getDaoSession().getUploadInfoDao().updateInTx(infos);
    }

    public List<BackupPhotoInfo> queryBackupPhotoInfo() {
        return CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder().where(BackupPhotoInfoDao.Properties.State.eq(false)).list();
    }

    public List<BackupPhotoInfo> queryAlreadyBackupPhotoInfo() {
        List<BackupPhotoInfo> lists = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder().where(BackupPhotoInfoDao.Properties.State.eq(true)).list();
        return lists;
    }

    public List<BackupPhotoInfo> queryAllBackupPhotoInfo() {
        List<BackupPhotoInfo> lists = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().loadAll();
        return lists;
    }

    public BackupPhotoInfo queryBackupPhotoInfoById(long id) {
        BackupPhotoInfo info = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder().where(BackupPhotoInfoDao.Properties.Id.eq(id)).unique();
        return info;
    }

    public BackupPhotoInfo queryOneBackupPhotoInfo() {
        List<BackupPhotoInfo> list = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder().where(BackupPhotoInfoDao.Properties.State.eq(false)).limit(1).list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 文件受损，上传下一个
     *
     * @return
     */
    public BackupPhotoInfo queryNextBackupInfo() {
        List<BackupPhotoInfo> list = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder().where(BackupPhotoInfoDao.Properties.State.eq(false)).limit(2).list();
        if (list.size() == 2) {
            return list.get(1);
        } else {
            //没有下一个，上传受损的
            return list.get(0);
        }
    }

    public void updateBackupPhoto(long id, BackupPhotoInfo backupPhotoInfo) {
        BackupPhotoInfo photoInfo = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder()
                .where(BackupPhotoInfoDao.Properties.Id.eq(id)).unique();
        if (photoInfo != null) {
            if (!photoInfo.getState()) {
                CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().update(backupPhotoInfo);
            }
        } else {
            insertBackupPhotoInfo(backupPhotoInfo);
        }
    }

    public void updateBackupPhotoInfo(long id, BackupPhotoInfo backupPhotoInfo) {
        BackupPhotoInfo photoInfo = CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().queryBuilder()
                .where(BackupPhotoInfoDao.Properties.Id.eq(id)).unique();
        if (photoInfo != null) {
            CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().update(backupPhotoInfo);
        } else {
            insertBackupPhotoInfo(backupPhotoInfo);
        }
    }

    public void insertBackupPhotoInfo(BackupPhotoInfo backupPhotoInfo) {
        CoCloudDatabaseLoader.getDaoSession().getBackupPhotoInfoDao().insert(backupPhotoInfo);
    }

    public List<RecentInfo> queryRecentInfo(String user, String did) {
        return CoCloudDatabaseLoader.getDaoSession().getRecentInfoDao().queryBuilder().where(RecentInfoDao.Properties.User.eq(user)
                , RecentInfoDao.Properties.Did.eq(did)).orderDesc(RecentInfoDao.Properties.Time).limit(AppConstants.RECENT_LIMIT).list();
    }

    public void updateRecentInfo(RecentInfo info) {
        RecentInfo recentInfo = CoCloudDatabaseLoader.getDaoSession().getRecentInfoDao().queryBuilder().where(RecentInfoDao.Properties.User.eq(info.getUser()),
                RecentInfoDao.Properties.Did.eq(info.getDid()), RecentInfoDao.Properties.FilePath.eq(info.getFilePath())).unique();
        if (recentInfo != null) {
            CoCloudDatabaseLoader.getDaoSession().getRecentInfoDao().update(info);
        } else {
            List<RecentInfo> recentInfoList = queryRecentInfo(info.getUser(), info.getDid());
            //判断个数是否合法，移除多余的
            if (recentInfoList != null && recentInfoList.size() >= AppConstants.RECENT_LIMIT) {
                for (int i = AppConstants.RECENT_LIMIT - 1; i < recentInfoList.size(); i++) {
                    CoCloudDatabaseLoader.getDaoSession().getRecentInfoDao().delete(recentInfoList.get(i));
                }
            }
            CoCloudDatabaseLoader.getDaoSession().getRecentInfoDao().insert(info);
        }
    }

    public void deleteAllRecent() {
        CoCloudDatabaseLoader.getDaoSession().getRecentInfoDao().deleteAll();
    }

    public MessageInfo insertMessage(MessageInfo info) {
        MessageInfo messageInfo = CoCloudDatabaseLoader.getDaoSession().getMessageInfoDao().queryBuilder().where(MessageInfoDao.Properties.Id.eq(info.getId()),
                MessageInfoDao.Properties.Uid.eq(info.getUid())).unique();

        if (messageInfo == null) {
            CoCloudDatabaseLoader.getDaoSession().getMessageInfoDao().insert(info);
            messageInfo = info;
        }

        return messageInfo;
    }

    public void updateMessage(MessageInfo info) {
        MessageInfo messageInfo = CoCloudDatabaseLoader.getDaoSession().getMessageInfoDao().queryBuilder().where(MessageInfoDao.Properties.Id.eq(info.getId()),
                MessageInfoDao.Properties.Uid.eq(info.getUid())).unique();
        if (messageInfo != null) {
            CoCloudDatabaseLoader.getDaoSession().getMessageInfoDao().update(info);
        }
    }

    public void deleteAllDevice() {
        CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().deleteAll();
    }

    public List<MessageInfo> getMessageList() {
        return CoCloudDatabaseLoader.getDaoSession().getMessageInfoDao().loadAll();
    }

    public List<CloudDevice> getCloudDeviceList() {
        return CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().loadAll();
    }

    public void deleteAllDevices() {
        List<CloudDevice> list = CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().queryBuilder().
                where(CloudDeviceDao.Properties.User_id.eq(AppConstantInfo.CURRENT_USER.getUser_id())).list();

        if (list != null && list.size() != 0) {
            CoCloudDatabaseLoader.getDaoSession().getCloudDeviceDao().deleteInTx(list);
        }
    }
}
