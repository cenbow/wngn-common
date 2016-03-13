package com.zzia.wngn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Util {

    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    protected static MessageDigest messagedigest = null;

    static {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nsaex) {
            logger.error(Md5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
            nsaex.printStackTrace();
        }
    }

    /**
     * 生成字符串的md5校验值
     * 
     * @param s
     * @return
     */
    public static String getMD5String(String s) {
        return getMD5String(s.getBytes());
    }

    /**
     * 判断字符串的md5校验码是否与一个已知的md5码相匹配
     * 
     * @param origin
     *            要校验的字符串
     * @param md5String
     *            已知的md5校验码
     * @return
     */
    public static boolean checkEqual(String origin, String md5String) {
        String s = getMD5String(origin);
        return s.equals(md5String);
    }

    /**
     * 生成文件的md5校验值
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            logger.error("对文件进行md5出错--------");
            throw e;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成文件的md5校验值
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getStreamMD5String(InputStream inputStream) throws Exception {
        try {
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            logger.error("对InputStream流进行md5出错--------");
            throw e;
        }

    }

    /**
     * 生成文件的md5校验值
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getStreamMD5StringWithClose(InputStream inputStream) throws Exception {
        try {
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buffer)) > 0) {
                messagedigest.update(buffer, 0, numRead);
            }
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            logger.error("对InputStream流进行md5出错--------");
            throw e;
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String getMD5String(byte[] bytes) {
        messagedigest.update(bytes);
        return bufferToHex(messagedigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
                                              // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

}