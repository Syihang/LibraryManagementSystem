package com.suyihang.file;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.suyihang.database.DataControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentImportDataListener extends AnalysisEventListener<StudentImport> {
    private List<StudentImport> studentList = new ArrayList<>();
    public static int successCount = 0; // 新增计数器用于记录成功插入的行数

    @Override
    public void invoke(StudentImport student, AnalysisContext context) {
        studentList.add(student);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData(studentList);
        System.out.println("成功导入的数据条数：" + successCount); // 打印成功导入的数据条数
    }

    private void saveData(List<StudentImport> students) {
        for (StudentImport student : students) {
            if (insertStudent(student)) {
                successCount++; // 每次成功插入增加计数
            }
        }
    }

    private boolean insertStudent(StudentImport student) {
        String sql = "INSERT INTO student (student_id, password, student_name, gender, faculty, year, telephone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataControl.openData();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentId());
            stmt.setString(2, student.getPassword());
            stmt.setString(3, student.getStudentName());
            stmt.setString(4, student.getGender());
            stmt.setString(5, student.getFaculty());
            stmt.setString(6, student.getYear());
            stmt.setString(7, student.getTelephone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0; // 返回插入成功与否的布尔值
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void importExcelToDatabase(String excelFilePath) {
        EasyExcel.read(excelFilePath, StudentImport.class, new StudentImportDataListener()).sheet().doRead();
    }
}
