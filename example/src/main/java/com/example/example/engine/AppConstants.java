package com.example.example.engine;

public class AppConstants {

    public final static String LOCAL_BASE = "/Android/data/com.example.example";
    public final static String EMOHOME_PATH = LOCAL_BASE + "/screenShots/";
    public final static String LOCAL_DOWNlOAD = LOCAL_BASE + "/downLoad";

    //dmc
    public final static String ELAPSED = "elapsed";
    public final static String DURATION = "duration";
    public final static String FAILURE = "failure";
    public final static String MEDIAMODEL = "meidaModel";
    public final static String VOLUME = "volume";
    public final static String STATUS_CODE = "StatusCode:";
    public final static String STATUS_MESSAGE = "StatusMessage:";
    public final static String PARAM_STRING = "paramString:";
    public final static String EXCEPTION = "Exception:";
    public final static String IMAGELIST = "images";
    public final static String SELECT_IMAGEINDEX = "selectIndex";

    // Request code
    public final static int SELECT_SHORTCUTS = 101;
    public final static int SELECT_PICTURE = 102;

    public final static String SELECTED_FUNCTION = "selected_functions";

    public final static String VIDEO_PATH = "videoPath";
    public final static String SELECTED_DEVICE = "device";

    public final static String TRANSLATE_THUMBNAIL_DATAS = "thumnnailDatas";
    public final static int EDITE_STATE = 1;
    public final static int SELECT_STATE = 2;
    //传给后台的数据
    public final static String TOKEN = "token";
    public final static String FILE_PATH = "filePath";
    public final static String FILE_DIRECT = "fileDirect";
    public final static String SEARCH = "search";
    public final static String TYPE = "type";
    public final static String PAGE_INDEX = "pageIndex";
    public final static String PAGE_NUM = "pageNum";
    public final static String PAGE_ROWS = "pageRows";
    public final static String SORT_MODE = "sortMode";
    public final static String SORT = "sort";
    public final static String SUFFIX = "suffix";

    public final static int EMPTY_MESSAGE = 0;

    public final static String SUCCESS_CODE = "100";
    public final static String CLOUD_DEVICE_TOKEN_EXPIRED_CODE = "604";
    public final static String TOKEN_EXPIRED_CODE = "605";
    public final static String USER_NOT_EXIST_EXPIRED_CODE = "603";
    public final static String PASSWORD_MODIFIED_TOKEN_EXPIRED_CODE = "612";//本地账号密码修改，token失效
    public final static String CLOUD_PASSWORD_MODIFIED_TOKEN_EXPIRED_CODE = "613";//云账号密码修改，当前盒子token失效
    public final static String CLOUD_SUCCESS_CODE = "200";
    public final static String ERROR_HAVE = "2001";
    public final static String ERROR_PATH = "2002";
    public final static String FILE_NOT_EXIST = "2003";
    public final static String SEARCH_ERROR = "999";
    public final static String ADD_SUCCESS = "1530";
    public final static String CLOUD_TOKEN_EXPIRED = "8407";

    public final static String INVALID_CLOUD_TOKEN = "8402";

    public final static String DOCUMENT_DETAIL = "documentDetail";
    public final static String DOCUMENT_TYPE = "documentType";
    public final static String DOCUMENT_POSITION = "position";

    //返回的文件类型“1”表示文件夹,“0”表示文件 或USB的0：未插入  1：已插入
    public final static String TYPE_FOLDER = "1";
    public final static String TYPE_FILE = "0";
    //请求数据时每页的数据个数
    public final static String PAGE = "10";
    //请求数据的排序方式  1：文件类型，2：文件名称，3.文件大小 , 4：文件日期
    public final static String SORT_TYPE = "1";
    public final static String SORT_NAME = "2";
    public final static String SORT_SIZE = "3";
    public final static String SORT_TIME = "4";

    //文件类型 0：所有，1：视频，2：音频，3：图片，4：文档，5：其它
    public final static String FILE_ALL = "0";
    public final static String FILE_VIDEO = "1";
    public final static String FILE_AUDIO = "2";
    public final static String FILE_PHOTO = "3";
    public final static String FILE_DOCUMENT = "4";
    public final static String FILE_OTHER = "5";
    //同名处理type
    public final static String SKIP = "1";
    public final static String COVER = "2";
    //排序方式 1是升序 0是降序
    public final static String SORT_ASCENDING = "1";
    public final static String SORT_DESCENDING = "0";

    public final static String REQUEST_CODE = "code";
    public final static String SOURCE_PATH = "sourcePath";
    public final static String TARGET_PATH = "targetPath";
    public final static String IS_USB = "isUSB";

    //get video list
    public final static String VIDEO_LIST = "videolist";
    public final static String VIDEO_DIRECTORY = "directory";
    public final static String VIDEO_SORT = "sort";
    public final static String VIDEO_CURRENT_PAGE = "page";
    public final static String VIDEO_CURRENT_POSITION = "position";
    public final static String SCREEN = "screen";
    public final static String FINAL_POSITION = "final_position";

    public final static String FILE_NAME = "fileName";
    public final static String FILE_ID = "id";
    public final static String FILE_PATH_SERVER = "filePathServer";
    public final static String FILE_PATH_CLIENT = "filePathClient";
    public final static String FILE_PATH_NAME_FINISH = "file_Name";
    public final static String FILE_SIZE = "fileSize";
    public final static String INDEX = "index";
    public final static String BLOCK_INDEX = "blockIndex";
    public final static String BLOCK_RANGE = "blockRange";
    public final static String FILE_MD5 = "fileMd5";
    public final static String SUB_FILE_NAME = "subFileName";
    public final static String BLOCK_MD5 = "blockMd5";
    public final static String FILE_FINISH = "file_finish";
    public final static int ALREADY_EXIST = -3;//上传文件时已经存在
    //手机文件的位置
    public final static int USB_INDEX = 0;
    public static final int MY_FILE_INDEX = 1;
    public final static int PHONE_INDEX = 2;
    public static final int SHARE_INDEX = 5;
    public static final int COLLECT_INDEX = 4;
    public static final int DOWNLOAD_INDEX = 6;
    //Samba文件
    public final static int SAMBA_INDEX = 7;
    public final static int BACKUP_INDEX = 3;
    public final static String FILE_EDITOR = "fileEditor";
    public final static String COLLECT_FILE = "collectFile";
    public final static String DELETE_FILE = "deleteFile";
    public final static String MOVE_FILE = "moveFile";
    public final static String SHARE_FILE = "shareFile";
    public final static String NEW_FILE = "newFile";
    public final static String COPY_FILE = "copyFile";
    public final static String RENAME_FILE = "renameFile";
    public final static String NEW_NAME = "newName";
    //是否共享   0：取消共享/收藏     1：共享/收藏
    public final static int CANCEL = 0;
    public final static int OK = 1;
    //0代表本地，1代表云 2代表解压
    public static final int LOCAL = 0;
    public static final int CLOUD = 1;
    public static final int DECOMPRESS = 2;
    public static final String CODE = "code";
    public static final String MSG = "msg";

    public static final String JSON_DATE = "date";
    public static final String JSON_OBJ = "obj";
    public static final String JSON_MSG = "msg";
    public static final String JSON_AC_NAME = "ac_name";
    public static final String LOGIN_FLG = "login_flg";
    public static final String CLOUD_TYPE = "cloud_open";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String GUID = "guid";
    public static final String JSON_LIST = "list";

    public static final String USER_NAME = "userName";
    public static final String AC_NAME = "acName";
    public static final String USER_NOTE = "userNote";
    public static final String USER_PASSWORD = "userPasswd";
    public static final String IS_RESET_CLOUD = "isCloud";
    public static final String NEW_PASSWORD = "newPasswd";
    public static final String CONFIRM_PASSWORD = "newPasswd2";
    public static final String PHONE = "phone";
    public static final String CLOUD_NAME = "cloudName";
    public static final String CLOUD_PASSWORD = "passwd";
    public static final String CERTIFICATE = "certificate";
    public static final String CERTIFY_CODE = "certifyCode";
    public static final String KEY = "key";
    public static final String IS_FORGET_PASSWORD = "isForgetPassword";
    public static final String IS_FROM_BIND_NUMBER = "isFromBindNumber";
    public static final String CLOUD_UID = "uid";
    public static final String SIGNSTRING = "signString";
    public static final String MOBILE = "mobile";
    public static final String CLOUD_DID = "did";
    public static final String CLOUD_DIDS = "dids";
    public static final String IS_CERTIFICATE = "isGetCertificate";
    public static final String IS_MESSAGE = "isMessage";

    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String USER_ID = "id";
    public static final String LOCAL_USER_NAME = "user_name";
    public static final String CLOUD_USER_NAME = "cloud_name";
    public static final String RAND_CODE = "rand_code";
    public static final String DEVICE_RAND_CODE = "randCode";
    public static final String DEVICE_ENCRY_CODE = "encryCode";

    public static final String IS_CLOUD_ACCOUNT = "cloud_account";

    public static final String UPDATE_SERVERAPK = "smarthome.apk";
    public static final String ACCOUNT = "account";

    public static final int INIT = 1;//定义四种下载的状态：初始化状态，正在下载状态，暂停状态，完成,出错状态
    public static final int DOWNLOADING = 2;
    public static final int PAUSE = 3;
    public static final int FINISH = 4;
    public static final int ERROR = 5;
    //0:未完成  1：已完成
    public static final int LOADING = 0;
    public static final int LOADING_FINISH = 1;

    public static final String FILE_INFO = "file_info";
    public static final String DOCUMENT = "document";

    // 删除用户类型
    public static final int DELETE_USER_AND_FILE = 1;
    public static final int DELETE_USER_SAVE_FILE = 2;

    public static final String SEARCH_TYPE = "searchType";
    public static final String EXIST_SEARCH = "0";
    public static final String NO_SEARCH = "1";
    public static final String IS_SET_REMOTE_ACCESS = "isSetRemoteAccess";
    public static final int OPEN_REMOTE_ACCESS = 1;//开启远程访问
    public static final int CLOSE_REMOTE_ACCESS = 0;//关闭远程访问

    public static final String DEFAULT_PATH = "defaultPath";
    public static final String SHOW_PHONE_FILE = "showPhoneFile";

    public static final int SELECTALL = 1;
    public static final int CANCELALL = 2;
    public static final int EDITMODE = 3;

    public static final int DOWNLOAD = 0;
    public static final int UPDOWNLOAD = 1;
    public static final int UPDATE_FINASH = 0;
    public static final int UPLOAD = 1;

    public static final int LEGAL = 1;
    public static final int NUMBER_ILLEGAL = 3;
    public static final int CONTENT_ILLEGAL = 2;
    public static final String ACTION_USER_SERVICE = "com.coclud.intent.action.USER.SERVICE";//修改用户
    public static final String ACTION_BACKUP_SERVICE = "com.coclud.intent.action.BACKUP.SERVICE";//备份
    public static final String ACTION_BACKUP_UPDATE = "com.coclud.intent.action.BACKUP.UPDATE";//备份数量变化
    public static final String ACTION_BACKUP_PHOTO = "com.coclud.intent.action.BACKUP.PHOTO";//备份开启状态
    public static final String ACTION_RECENT = "com.coclud.intent.action.RECENT";//快速访问的广播
    public static final String ACTION_RECENT_DELETE = "com.coclud.intent.action.RECENT.DELETE";//快速访问的广播
    public static final String ACTION_NICK_NAME_SERVICE = "com.coclud.intent.action.NICK.SERVICE";//修改昵称的广播
    public static final String ACTION_REMARK_SERVICE = "com.coclud.intent.action.REMARK.SERVICE";//修改修改用户备注的广播
    public static final String ACTION_REMARK_USER = "com.coclud.intent.action.REMARK.USER";//修改用户管理界面用户备注的广播
    public static final String BACKUP_PATH = "backup_path";
    public static final String ACTION_DOWNLOAD_SERVICE = "com.coclud.intent.action.DOWNLOAD.SERVICE";//下载
    public static final String ACTION_UPLOAD_SERVICE = "com.coclud.intent.action.UPLOAD.SERVICE";//上传
    public static final String BACKUP_SERVICE_PATH = "backup/";//备份的根目录

    public static final int URL_NOT_FOUND = 1;
    public static final int LOAD_ERROR = 2;
    public static final int ALL_FINISH = 3;
    public static final int SUB_FINISH = 4;
    public static final int LOAD_PAUSE = 5;
    public static final int QUERY_ONE = 6;
    public static final int INSERT_DOWNLOAD = 7;
    public static final int INSERT_UPLOAD = 8;
    public static final int DELETE_FINISH = 11;
    public static final int DELETE_LOADING = 12;
    public static final int QUERY_FINISH = 13;
    public static final int QUERY_NO = 14;
    public static final int UPDATE = 15;
    public static final int QUERY_UPLOAD_ID = 16;
    public static final int QUERY_DOWNLOAD_ID = 17;
    public static final int QUERY_RESUME = 18;
    public static final int LOADING_NEXT = 19;
    public static final int CLOSE = 20;
    public static final int LOAD_NEXT = 21;
    public static final int ERROR_WHAT = 22;
    public static final int CACHE = 23;
    public static final int WIFI_SPEED = 24;
    public static final int LOAD_MESSAGE = 25;

    public static final String RESULT = "result";
    public static final String NICKNAME = "nickname";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ACCESS_EXPIRES = "access_expires";
    public static final String THUNDER_REFRESH_TOKEN = "refresh_token";
    public static final String REFRESH_EXPIRES = "refresh_expires";
    public static final String SCOPE = "scope";
    public static final String STATE = "state";
    public static final String OPEN_FLG = "open_flg";
    public static final String BIND_FLG = "bind_flg";
    public static final String ACCESSTOKEN = "accessToken";
    public static final String BOXNAME = "boxName";
    public static final String URL = "url";

    public static final int SELECT_THUNDER_BT_CODE = 101;

    //request code
    public static final int BT_REQUEST_CODE = 103;
    public static final int THUNDER_REQUEST_CODE = 104;

    public static final String SUBTASK = "subtask";
    public static final String THUNDER_RETURN = "rtn";
    public static final String JSON = "json";
    public static final String THUNDER_PID = "pid";
    public static final String THUNDER_CLIENT_ID = "client_id";
    public static final String THUNDER_SCOPE = "scope";
    public static final String THUNDER_V = "v";
    public static final String THUNDER_CT = "ct";
    public static final String THUNDER_CALLBACK = "callback";
    public static final String THUNDER_INFOHASH = "infohash";
    public static final String THUNDER_TASKINFO = "taskInfo";
    public static final String THUNDER_SUBLIST = "subList";
    public static final String THUNDER_NAME = "name";
    public static final String THUNDER_SIZE = "size";
    public static final String THUNDER_PATH = "path";
    public static final String THUNDER_BTSUB = "btSub";
    public static final String THUNDER_DOWNLOAD_PATH = "/mnt/sata";
    public static final String THUNDER_TASKS = "tasks";
    public static final String THUNDER_TASK_PROGRESS = "progress";
    public static final String THUNDER_GCID = "gcid";
    public static final String THUNDER_CID = "cid";
    public static final String THUNDER_FILE_SIZE = "filesize";
    public static final String IS_THUNDER = "isThunder";
    public static final String THUNDER_BIND_INFO = "thunder_bind_info";


    public static final String IMAGE_TYPE = "image/*";
    public static final String PICTURE = "picture";
    public static final String PRIMARY = "primary";
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AUDIO = "audio";
    public static final String CONTENT = "content";
    public static final String FILE = "file";

    public static final int HD = 1;//高清
    public static final int SD = 2;//标清

    public static final String BACKUP_LIST = "backup_list";
    public static final int QUERY_PHOTO = 1;
    public static final int FINISH_BACKUP = 2;
    public static final int BACKUP_PHOTO = 1;

    //监控记录传入后台参数
    public static final String DEVICE_ID = "deviceId";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";

    public static final String RECORD_VOLUME = "recordVolume";
    public static final String RECORD_LOOP = "recordLoop";
    public static final String ALARM_VOLUME = "alarmVolume";
    public static final String ALARM_LOOP = "alarmLoop";
    public static final String UNIT = "unit";

    public static final String RECORD_DATE = "recordDate";
    public static final int RESULT_CODE = 0;

    //监控录像预留空间类型
    public static final String HEAD_SPACE = "headSpace";
    public static final String SPACE_SETTING = "spaceSetting";
    public static final int VIDEO_HEAD_SPACE = 0;
    public static final int ALARM_HEAD_SPACE = 1;

    public static final int YEAR_MONTH_DAY = 0;//日期格式年月日
    public static final int MONTH_DAY = 1;//日期格式月日
    public static final int YEAR_MONTH = 2;//日期格式年月
    public static final int YEAR_MONTH_DAY_HOUR_MINUTE = 3;//日期格式年月日点分

    //监控记录数据类型：1.手动视频  2.模式视频  3.手动截图  报警视频、报警截图待定
    public static final String ALARM_VIDEO = "4";
    public static final String ALARM_PRINT = "5";
    public static final String MODEL_VIDEO = "2";
    public static final String HAND_VIDEO = "1";
    public static final String HAND_PRINT = "3";

    //表格
    public static final String BACKUP_TIME = "backup_time";
    public static final String PHOTO_BACKUP_TIME = "photo_backup_time";
    public static final String AUTO_PHOTO_BACKUP = "auto_photo_backup";
    public static final String BACKUP_TIME_STYLE = "yyyy-MM-dd HH:mm";
    public static final String BACKUP_WIFI = "back_wifi";
    public static final String BACKUP_LOW_BATTERY = "low_battery";
    //关闭服务
    public static final String STOP_SERVICE = "stopService";

    //插件管理
    //0:卸载 1：安装
    public static final String UNINSTALLED = "0";
    public static final String INSTALLED = "1";
    //1:安装在手机端 2：安装在服务端 3：两端都安装
    public static final String IN_PHONE = "1";
    public static final String IN_SERVER = "2";
    public static final String IN_PHONE_AND_SERVER = "3";
    public static final String UNBIND = "0";
    public static final int INT_UNBIND = 0;
    public static final String BIND = "1";
    public static final int INT_BIND = 1;
    public static final String PHONE_SIDE = "0";
    public static final String SERVER_SIDE = "1";
    public static final String ALL_PLUGIN = "2";
    public static final String PLUGIN_WATCH_INFO = "PluginWatchInfo";
    public static final String PLUGIN_ID = "pluginId";
    public static final String BOX_NAME = "ThunderBox";
    public static final String IS_TRUE = "isTrue";
    public static final String SWITCH = "switch";
    public static final String ISCHECKED = "isChecked";
    public static final String THUNDER_STATUS = "thunder_status";
    public static final String THUNDER = "thunder";
    public static final String SHORTCUT_THUNDER = "shortcut_thunder";
    public static final String PAGERADAPTER = "pagerAdapter";
    //安装成功的广播
    public static final String INSTALL_SUCCESS = "com.coclud.intent.action.INSTALL.SUCCESS";
    //刷新适配器广播
    public static final String REFRESH_ADAPTER_ADD = "com.coclud.intent.action.REFRESH.ADAPTER_ADD";
    public static final String REFRESH_ADAPTER_REDUCE = "com.coclud.intent.action.REFRESH.ADAPTER_REDUCE";

    public static final int DELETE_USER = 1;//删除用户
    public static final int DELETE_CAMERA = 2;//删除设备
    public static final int CLOSE_ALARM = 1;//关闭警报
    public static final int RESET_CAMERA = 2;//重启摄像头
    public final static int REUESTCODE = 1;
    public final static int RESULTCODE = 2;
    public final static String CAMERA_ALARM_FUNCTION = "camera_alarm_function";
    public final static String CAMERA_STATE = "camera_state";
    public final static int CAMERA_NORMAL_STATE = 1;//摄像头正常状态
    public final static int CAMERA_ABNORMAL_STATE = 0;//摄像头非正常状态
    public final static int NO_ALARM_STATE = 2;//没有报警功能

    public static final int ACTIVATE = 1;//激活摄像头
    public static final int SETPASSWORD = 2;//设置密码
    public static final String HOST_ADDRESS = "deviceAddition";

    public static final int LOCAL_RENAME = 1;
    public static final int CLOUD_RENAME = 2;
    public static final int HTTP_BLOCK_SIZE = 1024 * 1024;

    public static final String FILE_LIST = "fileList";

    //url的key
    public static final String KEY_GETWEATHERINFO = "getWeatherInfo";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_REMOTELOGIN = "Remotelogin";
    public static final String KEY_REMOTENOCAMERA = "RemoteNoCamera";
    public static final String KEY_REMOTECAMERA = "RemoteCamera";
    public static final String KEY_FILELIST = "FileList";
    public static final String KEY_DEVICEADD = "deviceAdd";
    public static final String KEY_FILESEARCH = "FileSearch";
    public static final String KEY_USB = "USB";
    public static final String KEY_DOWNLOADSTART = "downloadStart";
    public static final String KEY_DOWNLOADDATA = "downloadData";
    public static final String KEY_DOWNLOADCLOSE = "downloadClose";
    public static final String KEY_UPLOADSTART = "uploadStart";
    public static final String KEY_UPLOADDATA = "uploadData";
    public static final String KEY_UPLOADCLOSE = "uploadClose";
    public static final String KEY_GETPICTUREINFO = "getPictureInfo";
    public static final String KEY_FILEDECOMPRESS = "FileDecompress";
    public static final String KEY_FILEDELETE = "FileDelete";
    public static final String KEY_FILECOLLECT = "FileCollect";
    public static final String KEY_FILESHARE = "FileShare";
    public static final String KEY_FILERENAME = "FileRename";
    public static final String KEY_FILENEWCREATE = "FileNewCreate";
    public static final String KEY_FILEMOVE = "FileMove";
    public static final String KEY_FileCopy = "FileCopy";
    public static final String KEY_SHORTCUTLIST = "ShortCutList";
    public static final String KEY_SHORTCUTMOVE = "ShortCutMove";
    public static final String KEY_RESETPASSWORD = "ResetPassword";
    public static final String KEY_BINDPHONENUMBER = "BindPhoneNumber";
    public static final String KEY_SENDCODE = "SendCode";
    public static final String KEY_CHECKCODE = "CheckCode";
    public static final String KEY_PROXYCONTROL = "ProxyControl";
    public static final String KEY_SHORTCUT = "ShortCut";
    public static final String KEY_RECENT = "Recent";
    public static final String KEY_RECENTREMOVE = "RecentRemove";
    public static final String KEY_LOGINCLOUD = "LoginCloud";
    public static final String KEY_GETDEVICES = "GetDevices";
    public static final String KEY_SENDCLOUDCODE = "SendCloudCode";
    public static final String KEY_VERIFYDEVICECODE = "VerifyDeviceCode";
    public static final String KEY_SENDTEMPLATECODE = "SendTemplateCode";
    public static final String KEY_VERIFYTEMPLATECODE = "VerifyTemplateCode";
    public static final String KEY_GETCERTIFICATES = "GetCertificates";
    public static final String CERTIFICATES = "Certificates";
    public static final String KEY_FEEDBACK = "FeedBack";
    public static final String KEY_FILELOOPLIST = "fileLoopList";
    public static final String KEY_UPGRADE = "UpGrade";
    public static final String KEY_MODIFYPASSWORD = "ModifyPassword";
    public static final String KEY_USERDELETE = "UserDelete";
    public static final String KEY_GETCATEGORYINFO = "getCategoryInfo";
    public static final String KEY_GETBRANDINFO = "getBrandInfo";
    public static final String KEY_GETMODELINFO = "getModelInfo";
    public static final String KEY_PROXYRELEASE = "ProxyRelease";
    public static final String KEY_UNBINDCHECKCODE = "UnbindCheckCode";
    public static final String KEY_LOADDELETE = "loadDelete";
    public static final String KEY_MINEINFO = "mineInfo";
    public static final String KEY_PROXYSETTING = "proxySetting";
    public static final String KEY_CHECKCERTIFICATE = "CheckCertificate";
    public static final String KEY_GETAUTHTOKEN = "GetAuthToken";
    public static final String KEY_USERLIST = "UserList";
    public static final String KEY_USERADD = "UserAdd";
    public static final String KEY_NICKRENAME = "NickRename";
    public static final String KEY_NICKNAME = "NickName";
    public static final String KEY_FORGETPASSWORD = "ForgetPassword";
    public static final String KEY_GETTHUNDERTOKEN = "GetThunderToken";
    public static final String KEY_GETTHUNDEROPENSTATUS = "GetThunderOpenStatus";
    public static final String KEY_GETTHUNDERBINDSTATUS = "GetThunderBindStatus";
    public static final String KEY_OPENTHUNDER = "OpenThunder";
    public static final String KEY_BINDTHUNDER = "BindThunder";
    public static final String KEY_UNBINDTHUNDER = "UnBindThunder";
    public static final String KEY_CREATETHUNDERTASK = "CreateThunderTask";
    public static final String KEY_BTCHECK = "BtCheck";
    public static final String KEY_CREATEBTTASK = "CreateBtTask";
    public static final String KEY_GETTHUNDERTASKLIST = "GetThunderTaskList";
    public static final String KEY_PAUSETHUNDERTASK = "PauseThunderTask";
    public static final String KEY_STARTTHUNDERTASK = "StartThunderTask";
    public static final String KEY_DELETETHUNDERTASK = "DeleteThunderTask";
    public static final String KEY_PARSETASK = "ParseTask";
    public static final String KEY_CAMERADETAIL = "cameraDetail";
    public static final String KEY_CAMERASNAPSHOT = "cameraSnapshot";
    public static final String KEY_DEVICELIST = "deviceList";
    public static final String KEY_SWITCHMONITOR = "switchMonitor";
    public static final String KEY_SWITCHALARM = "switchAlarm";
    public static final String KEY_GETRECORDDATE = "GetRecordDate";
    public static final String KEY_GETRECORD = "GetRecord";
    public static final String KEY_RECORDSETTING = "RecordSetting";
    public static final String KEY_RECORDSETTINGSAVE = "RecordSettingSave";
    public static final String KEY_GETCAMERALIST = "GetCameraList";
    public static final String KEY_PTZCONTINUE = "ptzContinue";
    public static final String KEY_DEVICESETTING = "deviceSetting";
    public static final String KEY_PTZSTOP = "ptzStop";
    public static final String KEY_PTZTAP = "ptzTap";
    public static final String KEY_PLUGININSTALL = "PluginInstall";
    public static final String KEY_PLUGINWATCH = "PluginWatch";
    public static final String KEY_DEVICESAVE = "deviceSave";
    public static final String KEY_CAMERAREBOOT = "cameraReboot";
    public static final String KEY_DEVICEDEL = "deviceDel";
    public static final String KEY_PLUGINUNINSTALL = "PluginUninstall";
    public static final String KEY_CAMERATEST = "cameraTest";
    public static final String KEY_GETICONLIST = "getIconList";
    public static final String KEY_GETBACKGROUNDLIST = "getBackgroundList";
    public static final String TORRENT = "torrent";
    public static final String CAMERA_ROLLOVER = "cameraRollover";
    public static final String ROUTER_HOME_PAGE = "routerHomePage";
    public static final String ROUTER_SYSINFO = "sysInfo";
    public static final String GROUPING_ALL_DEV_BY_ROOM = "groupingAllDevByRoom";
    public static final String KEY_CAMERA_DISCOVERY = "discoveryCamera";
    public static final String KEY_GET_SMART_HOME_DATA = "getSmartHomeData";
    public static final String ROUTER_STORAGE_MANAGER = "storage";
    public static final String ROUTER_STORAGE_MANAGER_DETAIL = "storageDetail";
    public static final String KEY_GET_MESSAGE_LIST = "GetMessageList";
    public static final String KEY_GET_MESSAGE_URL = "GetMessageUrl";
    public static final String ROUTER_UPGRADE_CHECK = "upgradeCheck";
    public static final String ROUTER_UPGRADE_START = "upgradeStart";
    public static final String ROUTER_UPGRADE_RESULT = "upgradeResult";
    public static final String ROUTER_ADVANCE_SETTING = "advancedSetting";
    public static final String ROUTER_SYSDATE_SAVE = "sysdateSave";
    public static final String ROUTER_SAVE_INNER_ADDRESS = "innerSave";
    public static final String ROUTER_RESTART = "restart";
    public static final String ROUTER_UPNP_SAVE = "upnpSave";
    public static final String ROUTER_DHCP_SAVE = "dhcpSave";
    public static final String RESET_ROUTER = "resetRouter";
    public static final String SAVE_BACKUP_CONFIG = "backup";
    public static final String ROUTER = "router";
    public static final String ROUTER_BACKUP_RESTORE = "restore";
    public static final String KEY_REMARK = "remark";
    public static final String ROUTER_DEVICES_LIST = "devicesList";
    public static final String ACCESS_DEVICE_DETAIL = "accessDeviceDetail";
    public static final String SET_ALIAS = "setAlias";
    public static final String ROUTER_ADD_BLACK = "addBlack";
    public static final String ROUTER_DELETE_BLACK = "delBlack";
    public static final String ROUTER_BLACK_LIST = "blackList";
    public static final String SYSTEM_DATE_AUTO_SAVE = "sysdateAutoSave";
    public static final String GLOBALSSID = "globalSsid";
    public static final String KEY_GET_AP_LIST = "ApList";
    public static final String KEY_GET_GLOBAL_MAGNITUDE = "GlobalMagnitude";
    public static final String KEY_SET_GLOBAL_SSID = "setGlobalSsid";
    public static final String KEY_SET_GLOBAL_MAGNITUDE = "setGlobalMagnitude";
    public static final String KEY_GET_APINFO = "ApInfo";
    public static final String KEY_SET_LED_STATE = "ledFlash";
    public static final String KEY_CLICK_WPS = "clickWps";
    public static final String KEY_REBOOT_AP = "rebootAp";
    public static final String KEY_SET_AP_LOCATION = "setLocation";
    public static final String ROUTER_ONLINE_TYPE = "onlineType";
    public static final String ROUTER_WIDE_TYPE_SAVE = "wideTypeSave";
    public static final String ROUTER_AUTO_TYPE_SAVE = "autoTypeSave";
    public static final String ROUTER_MANUAL_TYPE_SAVE = "manualTypeSave";
    public static final String KEY_BOXNAME = "boxName";
    public static final String MODIFY_CLOUD_PAW = "ModifyCloudPaw";
    public static final String CHECK_RESTART_STATUS = "restartStatus";
    public static final String REFRESH_CLOUD_TOKEN = "RefreshToken";

    //创建迅雷任务result
    public static final int THUNDER_START = 0;
    public static final int THUNDER_REPEAT = 202;
    public static final int NOT_THUNDER_TASK = 3;

    public static final int FILE_NO_EXIST = 1;
    public static final int FILE_POOR = 2;
    public static final int ROOM_NOT_ENOUGH = 3;
    public static final int ERROR_LOAD = 4;
    public static final int SERVER_LINK_ERROR = 5;

    public static final String BACKUP_VIEW = "backup";

    public static final String IP = "ip";
    public static final String PORT = "port";

    public static final int RECENT_LIMIT = 9;
    public static final String RECENT_INFO = "RecentInfo";

    public static final int SET_REMOTE_ACCESS_CLOSE = 1;//关闭远程访问dialog
    public static final int SET_REMOTE_ACCESS_NEED_BIND = 2;//开启远程访问需要 先绑定手机dialog
    public static final int UPDATE_ROUTE_NEET_RESET = 3;//升级路由器，需要重启dialog
    public static final int MODIFY_INNER_ADDRESS = 4;//修改内网地址dialog
    public static final int CONFIG_BACKUP = 5;//配置还原dialog
    public static final String CONFIG_BACKUP_CONTENT = "config_backup_content";//配置还原dialog内容
    public static final String CONFIG_BACKUP_TITLE = "config_backup_title";//配置还原dialog的title
    public static final String DEVICE_MAC = "mac";
    public static final String DEVICE_STA_MAC = "stamac";
    public static final String SUGGEST = "suggest";

    public static final String PROGRESS_TYPE = "progress_type";
    public static final int PLUGIN_INSTALL_PROGRESS = 1;//安装插件progress
    public static final int UPDATE_ROUTE_PROGRESS = 2;//升级progress
    public static final int RESTART_ROUTE_PROGRESS = 3;//重启路由progress
    public static final int RESTART_AP_PROGRESS = 4;//重启APprogress
    public static final int COMMITTING = 5;//正在提交中
    public static final int RESTORE_FACTORY_SETTING = 6;//恢复出厂设置
    public static final int LOADCONFIG = 7;//载入配置文件

    public static final String ROUTE_ADVANCED_SETTING_INFO = "route_advance_setting_info";//路由高级设置
    public static final String MODIFY_INNER_ADDRESS_STR = "modify_inner_address";

    public static final String UMENG_PUSH = "push";
    public static final String ALERT_PUSH = "alertPush";
    public static final String CURRENT_USER = "current_user";
    public static final String COCLOUD_MOBILE = "COCLOUD_MOBILE";

    public static final String REMARK = "remark";
    public static final String LIST = "list";

    public static final String FILEID = "fileId";
    public static final String CER_STRING = "cerString";
    public static final int MESSAGE = 3;

    public static final String LOAD_ID = "id";
    public static final String LOAD_STATE = "state";
    public static final String LOAD_COMPLETE = "complete";
    public static final String LOAD_TO_NEXT = "next";
    public static final String LOAD_RESUME = "isResume";
    public static final String LOAD_UPDATE = "isUpdate";
    public static final String LOAD_INFO = "info";
    public static final String FILE_LIST_RESULT = "fileListResult";

    public static final String REOPEN = "reOpen";
    public static final String UPLOAD_SERVICE_PACKAGE = "com.example.example.services.UploadService";
    public static final String DOWNLOAD_SERVICE_PACKAGE = "com.example.example.services.DownloadService";

    public static final int TIME_PICKER = 1;//时间选择器
    public static final int DATE_PICKER = 2;//日期选择器
    public static final String TIME = "time";//时间
    public static final String DATE = "date";//日期

    public static final int FEEDBACK_DIALOG = 1;//反馈Dialog
    public static final int INPUT_IP_ERROR_DIALOG = 2;//输入Ip不正确dialog

    public static final String LED_STATE = "led_state";
    public static final String ONLINE_STATE = "online_stat";
    public static final String POWER = "power";
    public static final String SSID2 = "ssid2";
    public static final String SSID5 = "ssid5";
    public static final String PWD = "pwd";
    public static final String ENCRYPT = "encrypt";

    public static final String HARD_VER = "hardver";
    public static final String SOFT_VER = "softver";
    public static final String AP_INDEX = "_apindex";
    public static final String LED_ACT = "led_act";
    public static final String WPS_ENABLE = "wps_enable";
    //picture
    public static final int PICTURE_RESULT_CODE = 10;
    public static final int PICTURE_REQUEST_CODE = 11;
    public static final int VIDEO_RESULT_CODE = 12;
    public static final int VIDEO_REQUEST_CODE = 13;
    public static final int AUDIO_RESULT_CODE = 14;
    public static final int AUDIO_REQUEST_CODE = 15;
    public static final int DOCUMENT_RESULT_CODE = 16;
    public static final int DOCUMENT_REQUEST_CODE = 17;
    public static final String COLLECT_FLG = "collectflg";//收藏标示
    public static final String POSITION = "position";

    public static final int CHANGE_STATE = 0;
    public static final int GAIN_STATE = 1;

    public static final String LOADING_STATE = "com.coclud.intent.action.LOADING_STATE";//改变下载、上传状态
    public static final String DOWNLOAD_ALL_STATE = "download_State";
    public static final String UPLOAD_ALL_STATE = "upload_State";
    public static final int ALL_START = 1;
    public static final int ALL_PAUSE = 2;

    public static final String IP_ADDR = "ipAddr";
    public static final String MASK_ADDR = "maskAddr";
    public static final String GATE_WAY_ADDR = "gatewayAddr";
    public static final String FIRST_DNS = "firstDns";
    public static final String SECOND_DNS = "secondDns";
    public static final String NET_TYPE = "net_type";

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String BIND_NUMBER = "0";
    public static final String GET_PASSWORD = "1";
    public static final String RESET_PASSWORD = "2";
    public static final String UNBIND_NUMBER = "3";
    public static final String IS_MODIFY_CLOUD_PASSWORD = "modifyCloudPassword";

    public static final String CLIPPER = "clipper";
    public static final String ACNAME = "ac_name";
    public static final String SAMBA_PATH = "public";
    public static final String BACK_UP_PATH = "backup/";
    public static final String THUNDER_LOAD_PATH = "TDDOWNLOAD";
    public static final String USB_PATH = "USB";

    public static final String MONITOR_FROM = "monitor_from";

    public static final int DELETE_SUCCESS = 3;
    public static final String SHARE = "share";

    public static final String IS_LOCAL = "isLocal"; // 是本地文件还是私有云文件

    public static final String FLG = "flg";

    public static final int RESPONSE_DLNA = 101;
    public static final int REQUEST_DLNA = 102;

    public static final String FOLDER_NAME = "folder_name";
}
