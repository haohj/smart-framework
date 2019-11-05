package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.utils.CastUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 */
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 获取客户列表
     */
    public List<Customer> getCustomerList() {
        Connection conn = DatabaseHelper.getConnection();
        try {
            String sql = "select * from customer";
            List<Customer> customerList = DatabaseHelper.queryEntityList(Customer.class, conn, sql);
            return customerList;
        } finally {
            DatabaseHelper.closeConnection(conn);
        }
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(long id) {
        Connection conn = null;
        try {
            String sql = "select * from customer where id=?";
            conn = DatabaseHelper.getConnection();
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
                DatabaseHelper.closeConnection(conn);
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
            conn = DatabaseHelper.getConnection();
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
                DatabaseHelper.closeConnection(conn);
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
            conn = DatabaseHelper.getConnection();
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
                DatabaseHelper.closeConnection(conn);
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
            conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            return stmt.execute(sql);
        } catch (SQLException e) {
            log.error("execute sql failure", e);
        } finally {
            if (conn != null) {
                DatabaseHelper.closeConnection(conn);
            }
        }
        return false;
    }
}
