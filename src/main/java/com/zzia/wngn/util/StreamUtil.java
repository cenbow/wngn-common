package com.zzia.wngn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StreamUtil {

    public static boolean builderFile(List<String> context, String filePath) {
        FileOutputStream out = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                mkdir(file.getParentFile());
                file.createNewFile();
            }
            out = new FileOutputStream(file, true);
            for (int i = 0; i < context.size(); i++) {
                out.write((context.get(i) + "\n").getBytes("utf-8"));
            }
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean builderFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                mkdir(file.getParentFile());
                file.createNewFile();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void mkdir(File file) {
        if (file.exists() && file.isDirectory()) {
            return;
        }
        file.mkdirs();
    }

    public static boolean move(String srcFile, String destPath) {
        File file = new File(srcFile);
        File dir = new File(destPath);
        boolean success = file.renameTo(new File(dir, file.getName()));
        return success;
    }

    public static boolean copy(String sourceFilePath, String targetFilePath) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            File sourceFile = new File(sourceFilePath);
            if (sourceFile.exists()) {
                inputStream = new FileInputStream(sourceFilePath);
                outputStream = new FileOutputStream(targetFilePath);
                int byteread = 0;
                byte[] buffer = new byte[1024];
                while ((byteread = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, byteread);
                }
                outputStream.flush();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}
