package com.example.example.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.example.engine.ExampleApplication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetUtils {
    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否连接可用
     *
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo != null) {
                NetworkInfo.State state = networkInfo.getState();
                if (state != null) {
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断手机网络是否连接可用
     *
     * @return
     */
    public static boolean isMobileNetConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                NetworkInfo.State state = networkInfo.getState();
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getWiFiSSID() {
        WifiManager mWifiManager = (WifiManager) ExampleApplication.getApplication().getSystemService(Context.WIFI_SERVICE);
        WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
        String CurInfoStr = mWifiInfo.toString();
        String CurSsidStr = mWifiInfo.getSSID();
        if (CurInfoStr.contains(CurSsidStr)) {
            return CurSsidStr;
        } else {
            return CurSsidStr.replaceAll("\"", "");
        }

    }

    public static String getGatewayIp() {
        WifiManager mWifiManager = (WifiManager) ExampleApplication.getApplication().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo di = mWifiManager.getDhcpInfo();
        long getewayIpL = di.gateway;
        String sb = String.valueOf((int) (getewayIpL & 0xff)) +
                '.' +
                String.valueOf((int) ((getewayIpL >> 8) & 0xff)) +
                '.' +
                String.valueOf((int) ((getewayIpL >> 16) & 0xff)) +
                '.' +
                String.valueOf((int) ((getewayIpL >> 24) & 0xff));
        return sb;
    }

    public static String getIp() {
        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {

                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements(); ) {

                    InetAddress inetAddress = ipAddr.nextElement();
                    // ipv4地址
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {

                        return inetAddress.getHostAddress();

                    }

                }

            }

        } catch (Exception ex) {

        }

        return "";

    }

    public static boolean isConnectionOuterNet() {
        try {
            Process p = Runtime.getRuntime().exec("/system/bin/ping -c 1 www.baidu.com");
            return p.waitFor() == 0;
        } catch (IOException e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static String getMac() {
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while ((line = input.readLine()) != null) {
                macSerial += line.trim();
            }
            input.close();
        } catch (IOException e) {

        }

        return macSerial;
    }
}


