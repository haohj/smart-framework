package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.utils.CollectionUtil;
import org.smart4j.chapter2.utils.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 */
public class DatabaseHelper {
    private static final Logger log = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final String DIRVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DIRVER = conf.getProperty("jdbc.dirver");
        URL = conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.username");
        PASSWORD = conf.getProperty("jdbc.password");
        try {
            Class.forName(DIRVER);
        } catch (ClassNotFoundException e) {
            log.error("can not load jdbc dirver", e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            log.error("execute sql failure", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.set(conn);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("close connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("query entity list failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 查询实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            log.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询语句
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            log.error("query entity failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 执行更新语句（包括update、insert、delete）
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            log.error("execute update failure", e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        //判断传入参数集合是否为空
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("can not insert entity:fieldMap is empty");
            return false;
        }
        //拼装插入语句
        String sql = "insert into" + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String key : fieldMap.keySet()) {
            columns.append(key).append(", ");
            values.append("?, ");
        }
        columns = columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values = values.replace(values.lastIndexOf(","), values.length(), ")");

        sql += columns + " values " + values;

        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        //判断传入参数集合是否为空
        if (CollectionUtil.isEmpty(fieldMap)) {
            log.error("can not update entity:fieldMap is empty");
            return false;
        }
        //拼装更新语句
        String sql = "update " + getTableName(entityClass) + "set ";
        StringBuilder columns = new StringBuilder();
        for (String key : fieldMap.keySet()) {
            columns.append(key).append("=?, ");
        }

        sql += columns.substring(0, columns.lastIndexOf(",")) + " where id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        return executeUpdate(sql, paramList.toArray()) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "delete from " + getTableName(entityClass) + " where id=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 获取实体对应的数据库表名
     */
    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }
}
