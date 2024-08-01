package com.suyihang.database;

import com.suyihang.entity.*;

import java.sql.*;
import java.util.ArrayList;

public class DataSelect {

    // 根据学生ID查找学生信誉表
    public static Credit seleceCreditbyStudentID(String ID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Credit credit = null;
        try {
            // 打开数据库连接
            con = DataControl.openData();

            // 准备查询SQL语句
            String query = "SELECT borrow_times, late_return_times, credit_value, credit_rating, borrow_limit FROM credit WHERE student_id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, ID);

            // 执行查询
            rs = pstmt.executeQuery();

            // 处理查询结果
            if (rs.next()) {
                int borrowTimes = rs.getInt("borrow_times");
                int lateReturnTimes = rs.getInt("late_return_times");
                int creditValue = rs.getInt("credit_value");
                String creditRating = rs.getString("credit_rating");
                int borrowLimit = rs.getInt("borrow_limit");

                credit = new Credit(borrowTimes, lateReturnTimes, creditValue, creditRating, borrowLimit);
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
        return credit;
    }

    // 通过学生ID查找该学生借阅的所有书籍记录
    public static ArrayList<Borrow> selectBorrowByStudentID(String id) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Borrow> borrowList = new ArrayList<>();
        try {
            con = DataControl.openData();
            stmt = con.createStatement();
            String query = "SELECT * FROM borrow WHERE student_id = '" + id + "'";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Borrow borrow = new Borrow();
                borrow.setBorrowId(rs.getInt("borrow_id"));
                borrow.setStudentId(rs.getString("student_id"));
                borrow.setISBN(rs.getString("ISBN"));
                borrow.setBorrowTime(rs.getDate("borrow_time"));
                borrow.setReturnTime(rs.getDate("return_time"));
                borrow.setActualReturnTime(rs.getDate("actual_return_time"));
                borrowList.add(borrow);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("Failed to close resources: " + e.getMessage());
            }
        }
        return borrowList;
    }

    // 查找该学生借阅总次数，即该学生ID在借阅表中出现的次数(true)
    // 查找书籍被借阅次数， 即书籍编号在借阅表中出现的次数(false)
    public static int getBorrowTimes(String Id, boolean isStudent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int borrowTimes = 0;
        String sql;

        try {
            conn = DataControl.openData();

            if (isStudent){
                sql = "SELECT COUNT(*) AS borrow_times FROM borrow WHERE student_id = ?";
            } else {
                sql = "SELECT COUNT(*) AS borrow_times FROM borrow WHERE ISBN = ?";
            }

//          sql = "SELECT COUNT(*) AS borrow_times FROM borrow WHERE student_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                borrowTimes = rs.getInt("borrow_times");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 可以根据具体情况处理异常
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return borrowTimes;
    }

    // 查找该学生延迟归还次数
    public static int getLateReturnTimes(String StrdentID){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int borrowTimes = 0;

        try {
            conn = DataControl.openData();

            String sql = "SELECT COUNT(*) AS late_return_times FROM borrow WHERE actual_return_time > return_time AND student_id = ?";

//          sql = "SELECT COUNT(*) AS borrow_times FROM borrow WHERE student_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, StrdentID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                borrowTimes = rs.getInt("late_return_times");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 可以根据具体情况处理异常
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return borrowTimes;
    }

    //通过学生ID查找学生
    public static Student findStudentByID(String id) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Student student = null;

        try {
            con = DataControl.openData();
            String query = "SELECT * FROM student WHERE student_id = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student(
                        rs.getString("student_id"),
                        rs.getString("password"),
                        rs.getString("student_name"),
                        rs.getString("gender"),
                        rs.getString("faculty"),
                        rs.getDate("year"),
                        rs.getString("telephone")
                );
            }
        } catch (SQLException e) {
            System.err.println("SQL 异常发生: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) DataControl.closeData(con);
            } catch (SQLException e) {
                System.err.println("关闭资源失败: " + e.getMessage());
            }
        }

        return student;
    }

    // 获取当前学生借出书籍的数量
    public static int getNumberStudentLent(String StudentID) {
        int numberOfBooksLent = 0;
        String sql = "SELECT COUNT(*) AS times_borrowed " +
                "FROM borrow " +
                "WHERE student_id = ? " +
                "AND actual_return_time IS NULL";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, StudentID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                numberOfBooksLent = rs.getInt("times_borrowed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberOfBooksLent;
    }


    // 通过账号查找管理员信息
    public static Admin findAdminByAccount(String account) {
        Admin admin = null;
        String sql = "SELECT admin_id, account, password, admin_name, telephone FROM admin WHERE account = ?";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int adminId = rs.getInt("admin_id");
                    String adminAccount = rs.getString("account");
                    String password = rs.getString("password");
                    String adminName = rs.getString("admin_name");
                    String telephone = rs.getString("telephone");

                    admin = new Admin(adminId, adminAccount, password, adminName, telephone);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }

    // 查找所有学生
    public static ArrayList<Student> findAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT student_id, password, student_name, gender, faculty, year, telephone FROM student";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String password = rs.getString("password");
                String studentName = rs.getString("student_name");
                String gender = rs.getString("gender");
                String faculty = rs.getString("faculty");
                Date year = rs.getDate("year");
                String telephone = rs.getString("telephone");

                Student student = new Student(studentId, password, studentName, gender, faculty, year, telephone);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // 查找全部丢失损坏信息
    public static ArrayList<Lose> findAllLose() {
        ArrayList<Lose> loses = new ArrayList<>();
        String sql = "SELECT student_id, ISBN, status, report_time, is_compensation, remark FROM lose";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String ISBN = rs.getString("ISBN");
                String status = rs.getString("status");
                Date reportTime = rs.getDate("report_time");
                boolean isCompensation = rs.getBoolean("is_compensation");
                String remark = rs.getString("remark");

                Lose lose = new Lose(studentId, ISBN, status, reportTime, isCompensation, remark);
                loses.add(lose);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loses;
    }

    public static void main(String[] args) {
//        Credit credit = seleceCreditbyStudentID("S10001");

        // 查找学生借书次数
        System.out.println(getBorrowTimes("S10001", true));

        // 查找书籍被借阅次数
        System.out.println(getBorrowTimes("1234567890", false));

        System.out.println(getLateReturnTimes("S10001"));

        System.out.println(findStudentByID("S10001").toString());

        String account = "suyihang"; // 示例账号
        Admin admin = findAdminByAccount(account);
        if (admin != null) {
            System.out.println(admin);
        } else {
            System.out.println("Admin not found");
        }
    }
}
