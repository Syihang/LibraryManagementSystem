package com.suyihang.file;

import com.alibaba.excel.EasyExcel;

public class ExcelToDatabase {

    public static void importStudentToDatabase(String excelFilePath) {
        EasyExcel.read(excelFilePath, StudentImport.class, new StudentImportDataListener()).sheet().doRead();
    }

}
