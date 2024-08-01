package com.suyihang.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsert {

    /**
     * 插入借阅记录到借阅表
     *
     * @param studentID        学生ID
     * @param ISBN             图书ISBN
     * @param borrowTime       借阅时间
     * @param returnTime       应归还时间
     * @param actualReturnTime 实际归还时间（可为空）
     * @return 插入记录的结果，成功返回1，失败返回0
     */
    public static boolean insertBorrow(String studentID, String ISBN, Date borrowTime, Date returnTime, Date actualReturnTime) {
        String sql = "INSERT INTO borrow (student_id, ISBN, borrow_time, return_time, actual_return_time) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentID);
            stmt.setString(2, ISBN);
            stmt.setDate(3, borrowTime);
            stmt.setDate(4, returnTime);
            if (actualReturnTime != null) {
                stmt.setDate(5, actualReturnTime);
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            return stmt.executeUpdate() != 0;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 插入丢失记录表,若成功则返回true
    public static boolean insertLose(String studentId, String isbn, String status, Date reportTime, int isCompensation, String remark) {
        String sql = "INSERT INTO lose (student_id, ISBN, status, report_time, is_compensation, remark) VALUES (?, ?, ?, ?, ?, ?)";
        boolean success = false;

        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.setString(2, isbn);
            stmt.setString(3, status);
            stmt.setDate(4, new java.sql.Date(reportTime.getTime()));
            stmt.setInt(5, isCompensation);
            stmt.setString(6, remark);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


}
