package com.suyihang.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSelectLogin {
    // 通过用户名查找密码(学生、管理员)

    public static String findStudentPassword(String studentID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String password = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 准备查询SQL语句
            String query = "SELECT password FROM student WHERE student_id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, studentID);

            // 执行查询
            rs = pstmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return password;
    }

    public static String findAdminPassword(String account) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String password = null;

        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 准备查询SQL语句
            String query = "SELECT password FROM admin WHERE account = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, account);

            // 执行查询
            rs = pstmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 关闭结果集、声明和连接
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return password;
    }

}
