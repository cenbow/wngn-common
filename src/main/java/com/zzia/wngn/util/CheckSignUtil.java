/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.zzia.wngn.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证签名.
 * 
 * @author luhuanan
 */
public class CheckSignUtil {

    /** logger. **/
    private static final Logger logger = LoggerFactory.getLogger(CheckSignUtil.class);

    /**
     * 字符编码.
     */
    private static final String CHARSET = "UTF-8";

    /**
     * 生成MD5签名.
     * 
     * @param list List
     * @param key 密钥
     * @return MD5签名
     */
    public static String generateSignMd5(List<BasicNameValuePair> list, String key) {
        return generateSignMd5(list, key, CHARSET);
    }

    /**
     * 生成MD5签名.
     * 
     * @param list List
     * @param key 密钥
     * @param charset 字符集
     * @return MD5签名
     */
    public static String generateSignMd5(List<BasicNameValuePair> list, String key, String charset) {
        String paramStr = transferToStr(list) + "key=" + key;
        logger.debug("paramStr  = " + paramStr);
        String sign = DigestUtil.md5(paramStr, charset);
        logger.debug(charset + " md5 sign  = " + sign);
        return sign;
    }
    
    /**
     * 生成MD5签名.
     * 
     * @param paramStr 待签名字符串
     * @param key 密钥
     * @param charset 字符集
     * @return MD5签名
     */
    public static String generateSignMd5(String paramStr, String key, String charset) {
        logger.debug("paramStr  = " + paramStr);
        paramStr = paramStr + "key=" + key;
        String sign = DigestUtil.md5(paramStr, charset);
        logger.debug(charset + " md5 sign  = " + sign);
        return sign;
    }

    /**
     * 生成MD5签名.
     * 
     * @param map Map
     * @param key 密钥
     * @return MD5签名
     */
    public static String generateSignMd5(Map<String, String> map, String key) {
        return generateSignMd5(map, key, CHARSET);
    }

    /**
     * 生成MD5签名.
     * 
     * @param map Map
     * @param key 密钥
     * @param charset 字符编码
     * @return MD5签名
     */
    public static String generateSignMd5(Map<String, String> map, String key, String charset) {
        List<BasicNameValuePair> list = map2List(map);
        String paramStr = transferToStr(list) + "key=" + key;
        logger.debug("paramStr  = " + paramStr);
        String sign = DigestUtil.md5(paramStr, charset);
        logger.debug(charset + " md5 sign  = " + sign);
        return sign;
    }

    /**
     * 生成SHA1签名.
     * 
     * @param list List
     * @param key 密钥
     * @return SHA1签名
     */
    public static String generateSignSha1(List<BasicNameValuePair> list, String key) {
        return generateSignSha1(list, key, CHARSET);
    }

    /**
     * 生成SHA1签名.
     * 
     * @param list List
     * @param key 密钥
     * @param charset 字符编码
     * @return SHA1签名
     */
    public static String generateSignSha1(List<BasicNameValuePair> list, String key, String charset) {
        String paramStr = transferToStr(list) + "key=" + key;
        logger.debug("paramStr  = " + paramStr);
        String sign = DigestUtil.sha1(paramStr, charset);
        logger.debug(charset + " sha1 sign  = " + sign);
        return sign;
    }

    /**
     * 生成SHA1签名.
     * 
     * @param map Map
     * @param key 密钥
     * @return SHA1签名
     */
    public static String generateSignSha1(Map<String, String> map, String key) {
        return generateSignSha1(map, key, CHARSET);
    }

    /**
     * 生成SHA1签名.
     * 
     * @param map Map
     * @param key 密钥
     * @param charset 字符编码
     * @return SHA1签名
     */
    public static String generateSignSha1(Map<String, String> map, String key, String charset) {
        List<BasicNameValuePair> list = map2List(map);
        String paramStr = transferToStr(list) + "key=" + key;
        logger.debug("paramStr  = " + paramStr);
        String sign = DigestUtil.sha1(paramStr, charset);
        logger.debug(charset + " sha1 sign  = " + sign);
        return sign;
    }

    /**
     * map转换为list（按key的字母顺序）.
     * 
     * @param map Map
     * @return 转换结果
     */
    public static List<BasicNameValuePair> map2List(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> treeMap = new TreeMap<String, String>(map);
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>(treeMap.size());
        Iterator<Entry<String, String>> iter = treeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    /**
     * 将list中的键值对拼成url中的参数列表字符串.
     * 
     * @param list List
     * @return url参数字符床
     */
    public static String transferToStr(List<BasicNameValuePair> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (BasicNameValuePair pair : list) {
            if (pair.getValue() == null) {
                buffer.append(pair.getName() + "=");
            } else {
                buffer.append(pair.getName());
                buffer.append("=");
                buffer.append(pair.getValue());
            }
            buffer.append("&");
        }
        return buffer.toString();
    }

}
