package com.example.example.engine;

public class MusicConstants {

    public static final int PLAY_MSG = 1;//播放
    public static final int PAUSE_MSG = 2;//暂停
    public static final int STOP_MSG = 3;//停止
    public static final int PROGRESS_CHANGE_PLAYING = 4;//进度改变
    public static final int PREVIOUS_MSG = 6;//上一首
    public static final int NEXT_MSG = 7;//下一首
    public static final int CONTINUE_PLAY_MSG = 8;//继续播放
    public static final int RANDOM_PLAY = 9;//随机播放
    public static final int SINGLE_PLAY = 10;//单曲播放
    public static final int ORDER_PLAY = 11;//顺序播放
    public static final int MODE_MSG = 12;//播放模式
    public static final int SLEEP_MSG = 13; //睡眠模式
    public static final int SELECT_MUSIC = 14;//选歌播放
    public static final String ACTION_MUSIC_SERVICE = "com.coclud.intent.action.MUSIC.SERVICE";
    public static final String MUSIC_CURRENT = "com.coclud.intent.action.MUSIC_CURRENT"; // 音乐当前时间改变动作
    public static final String MUSIC_COMPLETE = "com.coclud.intent.action.MUSIC_COMPLETE"; // 音乐播放完成
    public static final String MUSIC_ERROR = "com.coclud.intent.action.MUSIC_ERROR"; // 音乐播放出错
    public static final String MUSIC_UNKNOWN = "<unknown>"; // 音乐的未知

    public static final String MUSIC_FINISH = "com.coclud.intent.action.MUSIC_FINISH"; // 音乐FINISH动作
    public static final String MUSIC_PAUSE = "com.coclud.intent.action.MUSIC_PAUSE"; // 音乐FINISH动作
    public static final String SHOW_LRC = "com.coclud.intent.action.SHOW_LRC"; // 通知显示歌词
    public static final String MSG = "msg";
    public static final String BUNDLE = "bundle";
    public static final String PATH = "path";//路径
    public static final String MUSIC_INFO_LIST = "musicInfoList";
    public static final String POSITION = "position";//播放那首歌曲
    public static final String VALUE = "value";//拖动进度的播放位置
    public static final String MODE = "mode";//播放模式
    public static final String CURRENT_TIME = "currentTime";//当前播放时间
    public static final String CURRENT_MUSIC = "currentMusic";//当前播放那首歌曲
    public static final String SLEEP_TIME = "sleep_time";//当前播放那首歌曲
    public static final String DURATION = "duration";//播放歌曲时长

    public static final String NOT_YET_IMPLEMENTED = "Not yet implemented";//没有绑定服务的异常
    public static final String SOURCE_MODE = "sourceMode";
    public static final String SOURCE_MODE_NET = "sourceModeNet";
    public static final String SOURCE_MODE_Local = "sourceModeLocal";

    public static final int TWO_HOUR = 2 * 60 * 60;
    public static final int ONE_HOUR = 60 * 60;
    public static final int HALF_HOUR = 30 * 60;
    public static final int TWENTY_MINUTE = 20 * 60;
    public static final int TIME_ClOSE = -1;
}
