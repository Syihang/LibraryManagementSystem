package com.suyihang.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataUpdate {

    // 根据借阅ID修改实际归还时间
    public static boolean updateBorrow(int borrowID, Date actualReturnTime) {
        String sql = "UPDATE borrow SET actual_return_time = ? WHERE borrow_id = ?";
        boolean success = false;

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (DataSelectBook.findActualReturnTimeByBorrowID(borrowID) != null) {
                return false;
            }

            stmt.setDate(1, new java.sql.Date(actualReturnTime.getTime()));
            stmt.setInt(2, borrowID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // 根据书籍ID修改书籍的应当归还时间
    public static boolean updateExpectedReturnTime(int borrowID, Date newExpectedReturnTime) {
        String sql = "UPDATE borrow SET return_time = ? WHERE borrow_id = ?";
        boolean success = false;

        if (DataSelectBook.findActualReturnTimeByBorrowID(borrowID) != null) {
            return false;
        }

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(newExpectedReturnTime.getTime()));
            stmt.setInt(2, borrowID);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public static boolean updateStudent(String studentID, String password, String name, String faculty, String telephone) {
        // Base SQL update query
        String sql = "UPDATE student SET ";

        // Variables to keep track of which fields are being set
        boolean setSomething = false;

        // Checking and appending fields to update
        if (password != null && !password.isEmpty()) {
            sql += "password = ?, ";
            setSomething = true;
        }
        if (name != null && !name.isEmpty()) {
            sql += "student_name = ?, ";
            setSomething = true;
        }
        if (faculty != null && !faculty.isEmpty()) {
            sql += "faculty = ?, ";
            setSomething = true;
        }
        if (telephone != null && !telephone.isEmpty()) {
            sql += "telephone = ?, ";
            setSomething = true;
        }

        // Remove the trailing comma and space
        if (setSomething) {
            sql = sql.substring(0, sql.length() - 2); // remove last ", "
        } else {
            // If nothing to update, return false
            return false;
        }

        // Append WHERE clause
        sql += " WHERE student_id = ?";

        boolean success = false;

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters based on what fields are being updated
            int parameterIndex = 1;
            if (password != null && !password.isEmpty()) {
                stmt.setString(parameterIndex++, password);
            }
            if (name != null && !name.isEmpty()) {
                stmt.setString(parameterIndex++, name);
            }
            if (faculty != null && !faculty.isEmpty()) {
                stmt.setString(parameterIndex++, faculty);
            }
            if (telephone != null && !telephone.isEmpty()) {
                stmt.setString(parameterIndex++, telephone);
            }

            // Set the WHERE clause parameter
            stmt.setString(parameterIndex, studentID);

            // Execute update
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

}
