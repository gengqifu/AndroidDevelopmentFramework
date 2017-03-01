package com.example.example.utils;

import java.security.PrivateKey;

import javax.crypto.Cipher;

public final class RSAUtils {
    public static byte[] encryptData(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception e) {
            return null;
        }
    }
}
