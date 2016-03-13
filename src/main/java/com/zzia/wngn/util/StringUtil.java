package com.zzia.wngn.util;

import java.math.BigDecimal;
import java.util.Collection;

public class StringUtil {

    /**
     * 将输入的金额计算成已分为单位的整型
     * 
     * @param money
     * @return
     */
    public static String parseMoney(String money) {
        BigDecimal b = new BigDecimal(money);
        return b.multiply(new BigDecimal("100")).toBigInteger() + "";
    }

    /**
     * 将输入的金额计算成已元为单位的字符串
     * 
     * @param money
     * @return
     */
    public static String formatMoney(String money) {
        BigDecimal b = new BigDecimal(money);
        return b.divide(new BigDecimal("100")).toString();
    }

    /**
     * 首字母小写
     * 
     * @param str
     * @return
     */
    public static String toFirstLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 字符是英文字母
     * 
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符串全部由英文字母组成
     * 
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (!isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串第一个字符是英文字母
     * 
     * @param str
     * @return
     */
    public static boolean firstIsLetter(String str) {
        char c = str.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字符是大写英文字母
     * 
     * @param c
     * @return
     */
    public static boolean isUpperLetter(char c) {
        if ((c >= 'A' && c <= 'Z')) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 截取字符串前缀
     * 
     * @param str
     *            目标字符串
     * @param prefix
     *            字符串前缀
     * @return
     */
    public static String substringPrefix(String str, String prefix) {
        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return null;
    }

    /**
     * 截取字符串后缀
     * 
     * @param str
     *            目标字符串
     * @param suffix
     *            字符串后缀
     * @return
     */
    public static String substringSuffix(String str, String suffix) {
        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return null;
    }

    /**
     * 截取字符串
     * 
     * @param str
     *            目标字符串
     * @param prefix
     *            索引字符串
     * @param later
     *            截取后面（true）,截取前面（false）
     * @return
     */
    public static String substring(String str, String prefix, boolean later) {
        if (!str.contains(prefix)) {
            return str;
        }
        if (later) {
            return str.substring(str.lastIndexOf(prefix) + prefix.length());
        } else {
            return str.substring(0, str.indexOf(prefix));
        }
    }

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
     * 处理null字符串
     * 
     * @param value
     * @return
     */
    public static String dealNull(String value) {
        return (value == null) ? "" : value;
    }

    /**
     * 处理空白字符串
     * 
     * @param value
     * @return
     */
    public static String dealEmpty(String value) {
        return (value != null && value.length() == 0) ? null : value;
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
        info = (info == null) ? info : (info + ":");
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
        info = (info == null) ? info : (info + ":");
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
        info = (info == null) ? info : (info + ":");
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

    public static String dealWindowPath(String sourcePath) {
        sourcePath = sourcePath.replace("//", "/");
        sourcePath = sourcePath.replace("\\", "/");
        sourcePath = sourcePath.replace("\\\\", "/");
        return sourcePath;
    }

    public static String dealLinuxPath(String sourcePath) {
        sourcePath = sourcePath.replace("//", "/");
        sourcePath = sourcePath.replace("\\", "/");
        sourcePath = sourcePath.replace("\\\\", "/");
        return sourcePath;
    }

    public static void main(String[] args) {
        System.err.println(trimToBoolean("a"));
    }
}
