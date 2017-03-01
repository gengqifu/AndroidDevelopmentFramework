package com.example.example.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MD5Utils {
    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String getFileMD5(File file) throws NoSuchAlgorithmException, IOException {
        if (!file.isFile()) {
            return null;
        }

        byte buffer[] = new byte[1024];
        int len;

        MessageDigest digest = MessageDigest.getInstance("MD5");
        FileInputStream in = new FileInputStream(file);
        while ((len = in.read(buffer)) != -1) {
            digest.update(buffer, 0, len);
        }
        in.close();

        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static Map getDirMD5(File file, boolean listChild) throws IOException, NoSuchAlgorithmException {
        if (!file.isDirectory()) {
            return null;
        }
        Map map = new HashMap();
        String md5;
        File files[] = file.listFiles();
        for (File f : files) {
            if (f.isDirectory() && listChild) {
                map.putAll(getDirMD5(f, true));
            } else {
                md5 = getFileMD5(f);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }

    private static String byteToString(byte[] bByte) {
        StringBuilder sBuffer = new StringBuilder();
        for (byte aBByte : bByte) {
            sBuffer.append(byteToArrayString(aBByte));
        }
        return sBuffer.toString();
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    public static String getMD5Code(String strObj) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return byteToString(md.digest(strObj.getBytes()));
    }
}
