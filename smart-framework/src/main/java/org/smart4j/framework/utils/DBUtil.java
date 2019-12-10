package org.smart4j.framework.utils;

import org.smart4j.framework.helper.ConfigHelper;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    //定义一个数据库连接
    private static Connection conn = null;

    //获取连接
    public static Connection getConnection() {
        try {
            Class.forName(ConfigHelper.getJdbcDirver());
            conn = DriverManager.getConnection(ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //关闭连接
    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
