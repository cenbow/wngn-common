package com.zzia.wngn.util;

import java.nio.charset.Charset;
import java.sql.Connection;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

public class SQLScriptUtil {

    /**
     * 执行sql脚本文件
     * 
     * @param conn
     *            数据库连接
     * @param sqlfile
     *            脚本文件路径 相对于classes
     */
    public static void execute(Connection conn, String sqlfile) {
        try {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setErrorLogWriter(null);
            runner.setLogWriter(null);
            Resources.setCharset(Charset.forName("UTF-8"));
            runner.runScript(Resources.getResourceAsReader(sqlfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
