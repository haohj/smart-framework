package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.smart4j.framework.utils.DBUtil.getConnection;

/**
 * 数据库操作助手类
 *
 * @author haohj
 * @date 2020-05-19 15:00
 */
public class DatabaseHelper {
    private static final Logger log = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>(){
        @Override
        protected Connection initialValue() {
            return getConnection();
        }
    };

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                log.error("commit transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                log.error("rollback transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * @param cls
     * @param sql 查询语句
     * @return
     */
    public static List<Customer> queryEntityList(Class<?> cls, String sql) {
        return null;
    }

    /**
     * @param cls
     * @param sql
     * @param params
     * @return
     */
    public static Customer queryEntity(Class<?> cls, String sql, Object... params) {
        return null;
    }

    /**
     * 插入数据
     *
     * @param cls
     * @param fieldMap
     * @return
     */
    public static boolean insertEntity(Class<?> cls, Map<String, Object> fieldMap) {
        return false;
    }

    /**
     * @param cls
     * @param id
     * @param fieldMap
     * @return
     */
    public static boolean updateEntity(Class<?> cls, Object id, Map<String, Object> fieldMap) {
        return false;
    }

    /**
     * @param cls
     * @param id
     * @return
     */
    public static boolean deleteEntity(Class<?> cls, Object id) {
        return false;
    }
}
