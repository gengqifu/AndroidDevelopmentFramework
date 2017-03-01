package com.unicocloud.dao;

public class DBFieldBeanConstant {


    /**
     * 本地账号
     */
    public final static class LocalUserInfo {
        public final static String TABLE_NAME = "LocalUser";
        public final static String USER_ID = "user_id";
        public final static String USER_NAME = "user_name";
        public final static String USER_PASSWORD = "user_password";
        public final static String LOGIN_FLAG = "login_flg";
        public final static String CLOUD_TYPE = "cloud_type";
        public final static String CLOUD_USER = "cloud_name";
        public final static String TOKEN = "token";
        public final static String REFRESH_TOKEN = "refresh_token";
        public final static String IS_CURRENT_ACCOUNT = "current_account";
        public final static String DEVICE_ID = "did";
    }

    /**
     * 远程登录
     */
    public final static class CloudDeviceInfo {
        public final static String TABLE_NAME = "CloudDevice";
        public final static String DID = "did";
        public final static String USER_ID = "user_id";
        public final static String ADDRESS = "address";
        public final static String ONLINE = "online";
        public final static String CERTIFICATE = "certificate";
        public final static String KEY = "key";
        public final static String TOKEN = "token";
        public final static String IS_CURRENT_DEVICE = "current_device";
        public final static String DNAME = "dname";
        public final static String IS_RELAY = "isRelay";
        public final static String IS_REMOTECAN = "isRemoteCan";
    }

    /**
     * 下载
     */
    public final static class DownloadInfo {
        public final static String TABLE_NAME = "DownloadInfo";
        public final static String ID = "id";
        public final static String USER_NAME = "userName";
        public final static String DID = "did";
        public final static String URL = "url";
        public final static String FILETYPE = "fileType";
        public final static String TYPE = "type";
        public final static String LOCALPATH = "localPath";
        public final static String FILESIZE = "fileSize";
        public final static String STATE = "state";
        public final static String COMPLETE = "complete";
        public final static String FILENAME = "fileName";
        public final static String CREATETIME = "createTime";
        public final static String ERRORMESSAGE = "errorMessage";
    }

    /**
     * 上传
     */
    public final static class UploadInfo {
        public final static String TABLE_NAME = "UploadInfo";
        public final static String ID = "id";
        public final static String USER_NAME = "userName";
        public final static String DID = "did";
        public final static String TYPE = "type";
        public final static String URL = "url";
        public final static String FILETYPE = "fileType";
        public final static String LOCALPATH = "localPath";
        public final static String FILESIZE = "fileSize";
        public final static String STATE = "state";
        public final static String COMPLETE = "complete";
        public final static String FILENAME = "fileName";
        public final static String CREATETIME = "createTime";
        public final static String MD5 = "md5";
        public final static String ERRORMESSAGE = "errorMessage";
    }

    /**
     * 图片的备份
     */
    public final static class BackupPhotoInfo {
        public final static String TABLE_NAME = "BackupPhotoInfo";
        public final static String ID = "id";
        public final static String FILE_NAME = "fileName";
        public final static String FILE_PATH = "filePath";
        public final static String FILE_SIZE = "fileSize";
        public final static String MD5 = "md5";
        public final static String STATE = "state";
        public final static String CANCEL_STATE = "cancelState";
    }

    public final static class RecentInfo {
        public final static String TABLE_NAME = "RecentInfo";
        public final static String FILE_TYPE = "fileType";
        public final static String USER = "user";
        public final static String DID = "did";
        public final static String FILE_PATH = "filePath";
        public final static String IS_LOCAL = "isLocal";
        public final static String TIME = "time";
        public final static String FILE_NAME = "fileName";
        public final static String ID = "id";
        public final static String CREATE_TIME = "createTime";
        public final static String FILE_SIZE = "fileSize";
        public final static String MUSIC_ALBUM = "musicAlbum";
        public final static String MUSIC_SINGER = "musicSinger";
        public final static String MEDIA_TIME = "mediaTime";
        public final static String COLLECT_FLG = "collectFlg";
        public final static String SHARE_FLG = "shareFlg";
    }

    public final static class MessageInfo {
        public final static String TABLE_NAME = "MessageInfo";
        public final static String ID = "id";
        public final static String DID = "did";
        public final static String IMGURL = "imgUrl";
        public final static String MESSAGE = "message";
        public final static String TIME = "time";
        public final static String TITLE = "title";
        public final static String TYPE = "type";
        public final static String VIDEO_FILEID = "videoFileId";
        public final static String VIDEO_URL = "videoUrl";
        public final static String READ_STATUS = "readStatus";
        public final static String UID = "uid";
        public final static String DELETE_STATUS = "deleteStatus";
    }

}
