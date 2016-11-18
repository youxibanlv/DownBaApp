package com.strike.downba_app.utils;

import android.text.TextUtils;
import android.util.Base64;

import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 安全工具.
 */
public class SecurityUtils {

    /**
     * Default encrypt string.
     *
     * @param content the content
     * @return the string
     */
    public static String defaultEncrypt(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] digestBytes = digest.digest(Constance.CLIENT_SECRET.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(digestBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] contentsByte = content.getBytes("UTF-8");

            byte[] result = cipher.doFinal(contentsByte);
            String resultStr = bytesToHex(result).toUpperCase();
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Print bytes.
     *
     * @param tag   the tag
     * @param bytes the bytes
     */
    public static void printBytes(String tag, byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i]);
            sb.append(",");
        }
        LogUtil.e("printBytes" + tag + " " + sb.toString());
    }

    /**
     * Md 5 string.
     *
     * @param pwd the pwd
     * @return the string
     */
    public static String MD5(String pwd) {
        try {
            return MD5.md5(pwd).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Bytes to hex string.
     *
     * @param b the b
     * @return the string
     */
    public static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte element : b) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Hex to bytes byte [ ].
     *
     * @param md5Value the md 5 value
     * @return the byte [ ]
     */
    public static byte[] hexToBytes(String md5Value) {
        byte[] bytes = new byte[16];
        if (!TextUtils.isEmpty(md5Value)) {
            int length = md5Value.length();
            if (length != 32) {
                return null;
            }
            for (int i = 0; i < length; i++) {

                StringBuilder sb = new StringBuilder();
                sb.append(md5Value.charAt(i));
                sb.append(md5Value.charAt(++i));
                String hex = sb.toString();
                int result = Integer.parseInt(hex, 16);
                bytes[i / 2] = (byte) result;
            }
            return bytes;
        }
        return null;
    }


    /**
     * Base 64 encode string.
     *
     * @param content the content
     * @return the string
     */
    public static String base64Encode(String content) {
        String result = "";
        if (!TextUtils.isEmpty(content)) {
            try {
                result = new String(Base64.encode(content.getBytes(Constance.DEFAULT_CHARSET), Base64.URL_SAFE), Constance.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Base 64 decode string.
     *
     * @param content the content
     * @return the string
     */
    public static String base64Decode(String content) {
        String result = "";
        if (!TextUtils.isEmpty(content)) {
            try {
                result = new String(Base64.decode(content.getBytes(Constance.DEFAULT_CHARSET), Base64.URL_SAFE), Constance.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 与服务器商定的AES128的加密方式
     *
     * @param content the content
     * @param keyStr  the key str
     * @return string string
     */
    public static String encrypt128(String content, String keyStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] digestBytes = digest.digest(keyStr.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(digestBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] contentsByte = content.getBytes("UTF-8");

            byte[] result = cipher.doFinal(contentsByte);
            String resultStr = bytesToHex(result).toUpperCase();
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 与服务器商定的AES128的解密方式
     *
     * @param content the content
     * @param keyStr  the key str
     * @return string string
     */
    public static String decrypt128(String content, String keyStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] digestBytes = digest.digest(keyStr.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(digestBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] bytes = hexToBytes(content);

            byte[] result = cipher.doFinal(bytes);
            String resultStr = new String(result, "UTF-8");
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
