package com.suyihang.util;

import com.suyihang.database.DataSelect;
import com.suyihang.entity.Borrow;
import com.suyihang.entity.Credit;
import com.suyihang.entity.Student;

import java.sql.Date;
import java.util.ArrayList;

import static com.suyihang.util.DateUtil.getCurrentTime;
import static com.suyihang.util.DateUtil.timeCompare;

public class StudentUtil {

    public static Student current_student = null;

    // 判断某学生是否存在逾期未归还书籍
    public static boolean haveNotReturn(String StudentID) {
        ArrayList<Borrow> borrows = DataSelect.selectBorrowByStudentID(StudentID);
        for (Borrow borrow:borrows) {
//            System.out.println(borrow.getActualReturnTime());
            if (borrow.getReturnTime() == null && timeCompare(borrow.getReturnTime(), getCurrentTime()) == 1) {
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args) {
        System.out.println(haveNotReturn("S10001"));
    }
}
