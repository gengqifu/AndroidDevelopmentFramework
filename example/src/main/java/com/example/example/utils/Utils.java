package com.example.example.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.example.example.db.LocalUser;
import com.example.example.engine.AppConstants;
import com.example.example.engine.DataBaseEngine;
import com.example.examplelib.utils.BaseUtils;
import com.example.examplelib.utils.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils extends BaseUtils {
    /**
     * @param value
     * @param defaultValue
     * @return integer
     * @throws
     * @Title: convertToInt
     * @Description: 对象转化为整数数字类型
     */
    public static int convertToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    //本方法判断自己的某个service是否已经运行
    public static boolean isWorked(Context context, String name) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * @param seconds 精确到秒的字符串
     * @param format  传递格式，传递为空则按照yyyy-MM-dd HH:mm:ss转换
     * @return
     */
    public static String formatTime(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty())
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    public static boolean isNum(String txt) {
        StringBuilder builder = new StringBuilder(txt);
        if (txt.contains(".")) {
            builder.deleteCharAt(builder.indexOf("."));
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(builder.toString());
        return m.matches();
    }

    //判断是否为云登录
    public static boolean isCloudLogin() {
        LocalUser user = DataBaseEngine.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUser_id();
            String cloudName = user.getCloud_name();
            if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(cloudName) && userId.equals(cloudName)) {
                return true;
            }
        }
        return false;
    }

    //判断密码是否合格
    public static boolean isPasswordCorrect(String password) {
        if (password.length() < 8) {
            return false;
        }

        if (password.matches("\\w+")) {
            Pattern p1 = Pattern.compile("[a-z]+");
            Pattern p2 = Pattern.compile("[A-Z]+");
            Pattern p3 = Pattern.compile("[0-9]+");
            Matcher m = p1.matcher(password);
            if (!m.find())
                return false;
            else {
                m.reset().usePattern(p2);
                if (!m.find())
                    return false;
                else {
                    m.reset().usePattern(p3);
                    return m.find();
                }
            }
        } else {
            return false;
        }
    }

    public static int isUserNameCorrect(String name) {
        int flag;
        if (name.length() >= 4 && name.length() <= 10) {
            if (name.matches("[A-Za-z0-9]{4,10}")) {
                flag = AppConstants.LEGAL;
            } else {
                flag = AppConstants.CONTENT_ILLEGAL;
            }
        } else {
            //个数不合法
            flag = AppConstants.NUMBER_ILLEGAL;
        }
        return flag;
    }

    //double 保留一位小数
    public static double changeDouble(Double dou) {
        NumberFormat nf = new DecimalFormat("0.0");
        dou = Double.parseDouble(nf.format(dou));
        return dou;
    }

    /**
     * 根据名字判断歌曲的类型
     *
     * @param name
     * @return 根据名字能取到格式放回格式，不能的返回空字符串
     */
    public static String getStyle(String name) {
        String style = "";
        if (name != null && name.length() > 0) {
            int index = name.lastIndexOf(".");
            if (index != -1) {
                style = name.substring(index + 1);
            } else {
                style = "";
            }
        } else {
            style = "";
        }
        return style;
    }

    public static String getName(String name) {
        int index = name.lastIndexOf(".");
        if (-1 != index) {
            name = name.substring(0, index);
        }
        return name;
    }

    /**
     * 得到文件的大小
     *
     * @param context
     * @param size    long
     * @return 1, 023 MB
     */
    public static String getFileSize(Context context, long size) {
        String fileSize = Formatter.formatFileSize(context, size);
        String company;
        String substring = fileSize.substring(fileSize.length() - 2, fileSize.length() - 1);
        String newSize;
        DecimalFormat decimalFormat = new DecimalFormat("#,###.0");
        if ("K".equals(substring) || "M".equals(substring) || "G".equals(substring) || "T".equals(substring)) {
            company = fileSize.substring(fileSize.length() - 2);
            String realSize = fileSize.substring(0, fileSize.length() - company.length()).trim();
            newSize = decimalFormat.format(Float.parseFloat(realSize));

        } else {
            company = "KB";
            newSize = decimalFormat.format(size / 1024F);
        }
        if (newSize.indexOf(".") == 0) {
            if (newSize.endsWith("0")) {
                newSize = "0";
            } else {
                newSize = 0 + newSize;
            }
        }
        return newSize + " " + company;
    }

    public static boolean isMobileNO(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][3578]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    public static String getStringByValues(int stringId) {
        return com.example.example.engine.ExampleApplication.getApplication().getString(stringId);
    }
}
