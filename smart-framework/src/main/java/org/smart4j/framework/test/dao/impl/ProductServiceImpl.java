package org.smart4j.framework.test.dao.impl;

import org.smart4j.framework.test.dao.ProductService;
import org.smart4j.framework.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductServiceImpl implements ProductService {
    private static final String UPDATE_PRODUCT_SQL = "update product set price=? where id=?";
    private static final String INSERT_LOG_SQL = "insert into log (created,description) values (?,?) ";

    @Override
    public void updateProductPrice(long id, int pric) {
        try {
            //获取数据库连接
            Connection conn = DBUtil.getConnection();
            //关闭自动提交事务
            conn.setAutoCommit(false);
            //执行操作
            //更新产品
            updateProduct(conn, UPDATE_PRODUCT_SQL, id, pric);
            //插入日志
            insertLog(conn, INSERT_LOG_SQL, "Create product.");
            //提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            DBUtil.closeConnection();
        }
    }

    private void insertLog(Connection conn, String insertLogSql, String logDescription) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(insertLogSql);
        pstmt.setString(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
        pstmt.setString(2, logDescription);
        int rows = pstmt.executeUpdate();
        if (rows != 0) {
            System.out.println("Insert log success!");
        }
    }

    private void updateProduct(Connection conn, String updateProductSql, long id, int pric) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(updateProductSql);
        pstmt.setInt(1, pric);
        pstmt.setLong(2, id);
        int rows = pstmt.executeUpdate();
        if (rows != 0) {
            System.out.println("Update product success!");
        }
    }

    public static void main(String[] args) {
        ProductService productService = new ProductServiceImpl();
        productService.updateProductPrice(1,3000);
    }
}
