package com.zzia.wngn.util;

import java.util.UUID;

public class UUIDUtil {
	/**
     * 产生UUID字符串
     * @return UUID字符串
     */
    public static String generateUUID() {
       return UUID.randomUUID().toString();
    }
    
    public static String generateFormatUUID() {
        return UUID.randomUUID().toString().replace("-", "");
     }
}
