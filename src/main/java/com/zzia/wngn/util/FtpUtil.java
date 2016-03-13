package com.zzia.wngn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpUtil {

    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    protected static int UPLOAD_ERROR = 0;
    protected static int UPLOAD_SUCCEED = 1;
    protected static int UPLOAD_NOFILE = 2;
    protected static int UPLOAD_FAILURE = 3;

    protected static String FTP_URL;
    protected static Integer FTP_PORT;
    protected static String FTP_USER;
    protected static String FTP_PWD;

    static {
        FTP_URL = ConfigUtil.getStringValue("ftp.url");
        FTP_PORT = ConfigUtil.getIntegerValue("ftp.port");
        FTP_USER = ConfigUtil.getStringValue("ftp.username");
        FTP_PWD = ConfigUtil.getStringValue("ftp.password");
    }

    public static FTPClient connect() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(FTP_URL, FTP_PORT);
        ftp.login(FTP_USER, FTP_PWD);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            logger.error("连接失败：FTP[{}:{}:{}:{}]", FTP_URL, FTP_PORT);
            return null;
        }
        return ftp;

    }

    public static void close(FTPClient ftp) throws IOException {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (Exception e) {
                logger.warn("关闭FTP连接", e);
            }
        }

    }

    /**
     * 从FTP服务器下载指定文件名的文件。
     * 
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return 成功下载返回true，否则返回false。
     * @throws IOException
     * @author 宋立君
     * @date 2014年06月25日
     */
    public static int downFile(String remotePath, String localPath) throws IOException {
        int result = 0;
        FTPClient ftp = null;
        OutputStream outputStream = null;
        try {
            ftp = connect();
            if (ftp == null) {
                return UPLOAD_ERROR;
            }
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();

            if (fs.length == 3) {
                for (FTPFile ff : fs) {
                    if (null != ff && null != ff.getName()) {
                        if(".".equals(ff.getName()) || "..".equals(ff.getName())){
                            continue;   
                        }
                        File localFile = new File(localPath + "/" + ff.getName());
                        outputStream = new FileOutputStream(localFile);
                        ftp.retrieveFile(ff.getName(), outputStream);
                        result = UPLOAD_SUCCEED;
                        break;
                    }
                }
            } else {
                result = UPLOAD_NOFILE;
            }
            ftp.logout();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            close(ftp);
        }
        return result;
    }

    /**
     * 从FTP服务器下载指定文件名的文件。
     * 
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return 成功下载返回true，否则返回false。
     * @throws IOException
     * @author 宋立君
     * @date 2014年06月25日
     */
    public static int downFile(String remotePath, String fileName, String localPath) throws IOException {
        int result = 0;
        FTPClient ftp = null;
        try {
            ftp = connect();
            if (ftp == null) {
                return UPLOAD_ERROR;
            }
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();
            FTPFile ff;
            for (int i = 0; i < fs.length; i++) {
                ff = fs[i];
                if (null != ff && null != ff.getName() && ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());
                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(localFile);
                        ftp.retrieveFile(ff.getName(), outputStream);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    result = UPLOAD_SUCCEED;
                    break;
                }
                result = UPLOAD_NOFILE;
            }
            ftp.logout();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ftp);
        }
        return result;
    }

    public static int uploadFile(String remotePath, String path, String fileName, File file) {

        logger.info("upload file: path=" + remotePath + "/" + path + ";fileName=" + fileName);
        int result = 0;
        FTPClient ftp = null;
        InputStream input = null;
        try {
            ftp = connect();
            if (ftp == null) {
                return UPLOAD_ERROR;
            }
            ftp.changeWorkingDirectory(remotePath);
            ftp.makeDirectory(path);
            ftp.changeWorkingDirectory(path);
            input = new FileInputStream(file);
            ftp.storeFile(fileName, input);
            ftp.logout();
            result = UPLOAD_SUCCEED;
        } catch (Exception e) {
            e.printStackTrace();
            result = UPLOAD_ERROR;
        } catch (Throwable e) {
            e.printStackTrace();
            result = UPLOAD_ERROR;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }
}
