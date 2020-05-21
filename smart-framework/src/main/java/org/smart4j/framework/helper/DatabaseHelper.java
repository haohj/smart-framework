package org.smart4j.framework.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
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
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>() {
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
    public static <T> List<T> queryEntityList(Class<T> cls, String sql) {
        Connection conn = CONNECTION_HOLDER.get();
        QueryRunner qr = new QueryRunner();
        try {
            List<T> list = qr.query(conn, sql, new BeanListHandler<T>(cls));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param cls
     * @param sql
     * @param params
     * @return
     */
    public static <T> T queryEntity(Class<T> cls, String sql, Object... params) {
        Connection conn = CONNECTION_HOLDER.get();
        QueryRunner qr = new QueryRunner();
        try {
            T t = qr.query(conn, sql, new BeanHandler<>(cls), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 插入数据
     *
     * @param cls
     * @param fieldMap
     * @return
     */
    public static <T> boolean insertEntity(Class<T> cls, Map<String, Object> fieldMap) {
        Connection conn = CONNECTION_HOLDER.get();
        QueryRunner qr = new QueryRunner();
        StringBuffer sb = new StringBuffer("insert into ").append(cls.getName()).append("(");
        for(String key:fieldMap.keySet()){
            sb.append(key).append(",");
            fieldMap.get(key);
        }
        sb.deleteCharAt(sb.length()-1).append(") values (");

        try {
            qr.insert(conn, "", new BeanHandler<>(cls));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param cls
     * @param id
     * @param fieldMap
     * @return
     */
    public static <T> boolean updateEntity(Class<T> cls, Object id, Map<String, Object> fieldMap) {
        Connection conn = CONNECTION_HOLDER.get();
        QueryRunner qr = new QueryRunner();
        return false;
    }

    /**
     * @param cls
     * @param id
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> cls, Object id) {
        Connection conn = CONNECTION_HOLDER.get();
        QueryRunner qr = new QueryRunner();
        try {
            //TODO 不完整，需要注解标注主键字段
            int i = qr.update(conn, "delete from " + cls.getName() + " where id=?", id);
            if (i > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
