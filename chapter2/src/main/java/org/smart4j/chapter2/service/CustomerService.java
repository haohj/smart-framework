package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.utils.CastUtil;
import org.smart4j.chapter2.utils.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 提供客户数据服务
 */
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private static final String DIRVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

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
     * 获取客户列表
     */
    public List<Customer> getCustomerList() {
        Connection conn = null;
        try {
            List<Customer> customerList = new ArrayList<>();
            String sql = "select * from customer";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelphone(rs.getString("telphone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("close connection failure", e);
                }
            }
        }
        return null;
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(long id) {
        Connection conn = null;
        try {
            String sql = "select * from customer where id=?";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            Customer customer = new Customer();
            while (rs.next()) {
                customer.setId(id);
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelphone(rs.getString("telphone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
            }
            return customer;
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("close connection failure", e);
                }
            }
        }
        return null;
    }

    /**
     * 创建客户
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        Connection conn = null;
        try {
            String sql = "insert into customer(id,name,contact,telphone,email,remark) values (?,?,?,?,?,?)";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, CastUtil.castLong(fieldMap.get("id")));
            stmt.setString(2, CastUtil.castString(fieldMap.get("name")));
            stmt.setString(3, CastUtil.castString(fieldMap.get("contact")));
            stmt.setString(4, CastUtil.castString(fieldMap.get("telphone")));
            stmt.setString(5, CastUtil.castString(fieldMap.get("email")));
            stmt.setString(6, CastUtil.castString(fieldMap.get("remark")));
            return stmt.execute(sql);
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("close connection failure", e);
                }
            }
        }
        return false;
    }

    /**
     * 更新客户
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        Connection conn = null;
        try {
            String sql = "update customer set name=?,contact=?,telphone=?,email=?,remark=? where id=?";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, CastUtil.castString(fieldMap.get("name")));
            stmt.setString(2, CastUtil.castString(fieldMap.get("contact")));
            stmt.setString(3, CastUtil.castString(fieldMap.get("telphone")));
            stmt.setString(4, CastUtil.castString(fieldMap.get("email")));
            stmt.setString(5, CastUtil.castString(fieldMap.get("remark")));
            stmt.setLong(6, CastUtil.castLong(fieldMap.get("id")));
            return stmt.execute(sql);
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("close connection failure", e);
                }
            }
        }
        return false;
    }

    /**
     * 删除客户
     */
    public boolean deleteCustomer(long id) {
        Connection conn = null;
        try {
            String sql = "delete from customer where id=?";
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            return stmt.execute(sql);
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    log.error("close connection failure", e);
                }
            }
        }
        return false;
    }
}
