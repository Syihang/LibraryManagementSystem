package com.suyihang.database;

import java.sql.*;

public class DataControl {

    // 打开数据库
    public static Connection openData() throws SQLException {
        Connection con = null;

        String url = "jdbc:mysql://127.0.0.1:3306/librarymanagementsystem?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        con = DriverManager.getConnection(url, username, password);

        return con;
    }

    // 关闭数据库
    public static boolean closeData(Connection con) {
        if (con != null) {
            try {
                con.close();
//                System.out.println("Database connection closed successfully");
                return con.isClosed();
            } catch (SQLException e) {
                System.err.println("Failed to close database connection: " + e.getMessage());
            }
        }
        return false; // Connection was null or closing failed
    }

}

