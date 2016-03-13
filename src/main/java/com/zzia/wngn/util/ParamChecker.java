/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zzia.wngn.util;

import java.util.Collection;

public class ParamChecker {

    /**
     * 判断字符串不为空
     * 
     * @param value
     * @return
     */
    public static boolean notBlank(String value) {
        return (value != null && !"".equals(value));
    }

    /**
     * 判断集合不为空
     * 
     * @param value
     * @return
     */
    public static boolean notBlank(Collection<?> collection) {
        return (collection != null && collection.size() > 0);
    }

    /**
     * 判断对象不为空
     * 
     * @param value
     * @return
     */
    public static boolean notBlank(Object value) {
        return (value != null && !"".equals(value.toString()));
    }

    /**
     * 判断对象不为空
     * 
     * @param value
     * @return
     */
    public static boolean notNull(Object value) {
        return value != null;
    }

    /**
     * 检测字符串参数不为空
     * 
     * @param str
     *            参数值
     * @param name
     *            参数名
     */
    public static void notEmpty(String value, String name) {
        notEmpty(value, name, null);
    }

    /**
     * 检测字符串参数不为空
     * 
     * @param value
     *            参数值
     * @param name
     *            参数名
     * @param info
     *            附加异常信息
     */
    public static void notEmpty(String value, String name, String info) {
        info = (info == null) ? "" : (info + ":");
        if (value == null) {
            throw new IllegalArgumentException(info + "字符串参数[" + name + "] cannot be null");
        }
        if (value.length() == 0) {
            throw new IllegalArgumentException(info + "字符串参数[" + name + "] cannot be empty");
        }
    }

    /**
     * 检测集合参数不为空
     * 
     * @param value
     *            参数值
     * @param name
     *            参数名
     */
    public static void notEmpty(Collection<?> value, String name) {
        notEmpty(value, name, null);
    }

    /**
     * 检测集合参数不为空
     * 
     * @param value
     *            参数值
     * @param name
     *            参数名
     * @param info
     *            附加异常信息
     */
    public static void notEmpty(Collection<?> value, String name, String info) {
        info = (info == null) ? "" : (info + ":");
        if (value == null) {
            throw new IllegalArgumentException(info + "集合参数[" + name + "] cannot be null");
        }
        if (value.size() == 0) {
            throw new IllegalArgumentException(info + "集合参数[" + name + "] cannot be empty");
        }
    }

    /**
     * 检测对象参数不为空
     * 
     * @param value
     *            参数值
     * @param name
     *            参数名
     */
    public static void notEmpty(Object value, String name) {
        notEmpty(value, name, null);
    }

    /**
     * 检测对象参数不为空
     * 
     * @param value
     *            参数值
     * @param name
     *            参数名
     * @param info
     *            附加异常信息
     */
    public static void notEmpty(Object value, String name, String info) {
        info = (info == null) ? "" : (info + ":");
        if (value == null) {
            throw new IllegalArgumentException(info + "对象参数[" + name + "] cannot be null");
        }
    }

    /**
     * 检测对象参数不为空
     * 
     * @param value
     *            参数值
     * @param name
     *            参数名
     */
    public static void notBothEmpty(String names, Object... values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null && values[i].toString().length() > 0) {
                return;
            }
        }
        throw new IllegalArgumentException("对象参数 [" + names + "] both is null");
    }

    /**
     * 字符串如果为空就返回""，否则还回该字符串
     * 
     * @param value
     *            参数值
     * @return
     */
    public static String trimToEmpty(String value) {
        if (!notBlank(value)) {
            return "";
        }
        return value.trim();
    }

    /**
     * 字符串如果为空就返回null，否则还回该字符串
     * 
     * @param value
     *            参数值
     * @return
     */
    public static String trimToNull(String value) {
        if (!notBlank(value)) {
            return null;
        }
        return value.trim();
    }

    /**
     * 字符串如果为空就返回0，否则转成int
     * 
     * @param value
     *            参数值
     * @return
     */
    public static int trimToInt(String value) {
        if (!notBlank(value)) {
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    /**
     * 字符串如果为空就返回0L，否则转成long
     * 
     * @param value
     *            参数值
     * @return
     */
    public static long trimToLong(String value) {
        if (!notBlank(value)) {
            return 0L;
        }
        return Long.parseLong(value.trim());
    }

    /**
     * 字符串如果为空就返回false，否则转成boolean
     * 
     * @param value
     *            参数值
     * @return
     */
    public static boolean trimToBoolean(String value) {
        if (!notBlank(value)) {
            return false;
        }
        if ("true".equalsIgnoreCase(value.trim()) || "1".equals(value.trim())) {
            value = "true";
        } else {
            value = "false";
        }
        return Boolean.parseBoolean(value.trim());
    }
}
