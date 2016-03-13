package com.zzia.wngn.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zzia.wngn.util.ParamChecker;
import com.zzia.wngn.util.StreamUtil;

/**
 * @author v_wanggang
 * @date 2015年12月10日 下午6:54:03
 */
public class CmdOperation {

    public static void main(String[] args) {
        try {
            List<String> list = new ArrayList<String>();
            list.add("D:");
            list.add(
                    "svn export -r 334 https://127.0.0.1:8443/svn/wngn/trunk/wngn-agile /temp/test/agile/334/source/ --username wngn --password wngn");
            list.add("cd /temp/test/agile/334/source/");
            list.add("mvn clean compile");
            list.add("mvn install");
            System.out.println(execBatchCommand(list, "D://temp//temp.cmd", "D://temp//temp.log"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行批量cmd命令
     * 
     * @param commands
     *            cd /temp/test/agile/334/source/
     * @param cmdFile
     *            D://temp//temp.cmd
     * @param logFile
     *            D://temp//temp.log
     * @return
     * @throws IOException
     */
    public static int execBatchCommand(String[] commands, String cmdFile, String logFile) throws IOException {
        List<String> list = null;
        if (commands != null && commands.length > 0) {
            list = new ArrayList<String>(commands.length);
            for (String string : commands) {
                list.add(string);
            }
        }
        return execBatchCommand(list, cmdFile, logFile);
    }

    /**
     * 执行批量cmd命令
     * 
     * @param commands
     *            cd /temp/test/agile/334/source/
     * @param cmdFile
     *            D://temp//temp.cmd
     * @param logFile
     *            D://temp//temp.log
     * @return
     * @throws IOException
     */
    public static int execBatchCommand(List<String> commands, String cmdFile, String logFile) throws IOException {
        ParamChecker.notEmpty(commands, "commands");
        try {
            if (StreamUtil.builderFile(commands, cmdFile)) {
                if (StreamUtil.builderFile(logFile)) {
                    return execCommand(cmdFile + " >> " + logFile);
                }
            }
        } finally {
            execCommand("del " + cmdFile.replace("//", "\\"));
        }
        return 0;
    }

    /**
     * 执行cmd命令
     * 
     * @param command
     *            dir del cd......
     * @return
     */
    public static int execCommand(String command) {
        try {
            String[] cmd = new String[] { "cmd.exe", "/c", command };
            return cmd(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 执行cmd命令
     * 
     * @param command
     *            java -jar ..
     * @return
     */
    public static int runCommand(String command) {
        try {
            String[] cmd = new String[] { "cmd.exe", "/c start", command };
            return cmd(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int cmd(String[] cmd) throws Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        new Thread(new StreamDrainer(process.getInputStream())).start();
        new Thread(new StreamDrainer(process.getErrorStream())).start();
        process.getOutputStream().close();
        return process.waitFor();
    }

}
