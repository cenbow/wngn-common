/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.zzia.wngn.util;

import java.security.MessageDigest;

/**
 * 加密签名.
 * 
 * @author luhuanan
 */
public class DigestUtil {

    /**
     * 字符编码.
     */
    private static final String CHARSET = "UTF-8";

    /**
     * md5签名.
     * 
     * @param s 字符串
     * @return md5
     */
    public static String md5(String s) {
        return md5(s, CHARSET);
    }

    /**
     * md5签名.
     * 
     * @param s 字符串
     * @param charset 字符编码
     * @return md5
     */
    public static String md5(String s, String charset) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput;
            if (charset == null || charset.trim().equals("")) {
                btInput = s.getBytes();
            } else {
                btInput = s.getBytes(charset);
            }

            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * sha1签名.
     * 
     * @param s 字符串
     * @return sha1
     */
    public static String sha1(String s) {
        return sha1(s, CHARSET);
    }

    /**
     * sha1签名.
     * 
     * @param s 字符串
     * @param charset 字符编码
     * @return sha1
     */
    public static String sha1(String s, String charset) {
        try {
            byte[] btInput;
            if (charset == null || charset.trim().equals("")) {
                btInput = s.getBytes();
            } else {
                btInput = s.getBytes(charset);
            }

            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("SHA1");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer sb = new StringBuffer(md.length * 2);
            for (int i = 0; i < md.length; i++) {
                sb.append(Character.forDigit((md[i] & 240) >> 4, 16));
                sb.append(Character.forDigit(md[i] & 15, 16));
            }
            return sb.toString();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

}
