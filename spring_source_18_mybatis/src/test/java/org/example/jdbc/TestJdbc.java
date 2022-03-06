package org.example.jdbc;

import org.example.entity.User;
import org.junit.Test;

import java.sql.*;
import java.util.Date;

public class TestJdbc {


    @Test
    public void test() throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 1.加载驱动
            //Class.forName("com.mysql.jdbc.Driver");

            // 2.创建连接
            // 使用了SPI机制，可以省略加载驱动这一步，在DriverManager的静态代码块中会去加载驱动
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myTestDB?characterEncoding=utf-8&useSSL=false", "root", "root");

            // 开启事务
            conn.setAutoCommit(false);

            // sql语句 参数#{}、${}、<if>
            String sql = "select * from user where id = ?";

            // 获得sql执行者
            // 1.执行预处理、设置参数
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);

            // 执行查询
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            // pstmt.executeQuery();
            rs.next();

            // 处理结果集
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSex(rs.getBoolean("sex"));
            user.setBirthday(rs.getTimestamp("birthday"));
            System.out.println(user);

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
