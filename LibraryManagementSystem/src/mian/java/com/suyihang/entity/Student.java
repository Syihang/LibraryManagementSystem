package com.suyihang.entity;

import com.suyihang.database.DataSelect;
import com.suyihang.util.StudentUtil;

import java.util.Date;

public class Student {

    // 学生基本信息
    private String studentId;  // 学号
    private String password;   // 密码
    private String studentName;  // 姓名
    private String gender;  // 性别
    private String faculty;  // 所在学院
    private Date year;  // 入学年份
    private String telephone;  // 联系电话

    // 学生信誉信息
    private int borrowTimes;  // 借书次数
    private int lateReturnTimes;  // 逾期次数
    private double creditValue;  // 信用值
    private String creditRating;  // 信用等级
    private int borrowLimit;  // 借书限制
    private int borrowedBookNum; // 在借书籍数量

    // 构造方法
    public Student() {}

    public Student(String studentId, String password, String studentName, String gender, String faculty, Date year, String telephone) {
        this.studentId = studentId;
        this.password = password;
        this.studentName = studentName;
        this.gender = gender;
        this.faculty = faculty;
        this.year = year;
        this.telephone = telephone;
        // 获取该学生的信誉表
        Credit credit = DataSelect.seleceCreditbyStudentID(studentId);
        // 判断该学生是否存在逾期未归还的书籍
        boolean haveOverdueNotReturn = StudentUtil.haveNotReturn(studentId);

        this.borrowTimes = DataSelect.getBorrowTimes(studentId, true);

        this.lateReturnTimes = DataSelect.getLateReturnTimes(studentId);

        this.creditValue = calculateCreditValue(this.borrowTimes, this.lateReturnTimes);

        this.creditRating = calculateCreditRating(this.creditValue);

        this.borrowLimit = calculateBorrowLimit(this.creditRating,haveOverdueNotReturn);

        this.borrowedBookNum = DataSelect.getNumberStudentLent(studentId);
    }

    private int calculateBorrowLimit(String creditRating, boolean haveOverdueNotReturn) {
        if (haveOverdueNotReturn) {
            return 0;
        }
        return switch (creditRating) {
            case "优秀" -> 4;
            case "良好" -> 3;
            case "一般" -> 2;
            case "及格" -> 1;
            default -> 0;
        };
    }

    private String calculateCreditRating(double creditValue) {
        if (creditValue >= 90) {
            return "优秀";
        } else if (creditValue >= 80) {
            return "良好";
        } else if (creditValue >= 70) {
            return "一般";
        } else if (creditValue >= 60) {
            return "及格";
        } else {
            return "不及格";
        }
    }

    private double calculateCreditValue(int borrowTimes, int lateReturnTimes) {
        double creditValue = 100 - lateReturnTimes * 5 + borrowTimes;
        return Math.min(creditValue, 100);
    }

    // Getter 和 Setter 方法
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getBorrowTimes() {
        return borrowTimes;
    }

    public void setBorrowTimes(int borrowTimes) {
        this.borrowTimes = borrowTimes;
    }

    public int getLateReturnTimes() {
        return lateReturnTimes;
    }

    public void setLateReturnTimes(int lateReturnTimes) {
        this.lateReturnTimes = lateReturnTimes;
    }

    public double getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(double creditValue) {
        this.creditValue = creditValue;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    public int getBorrowLimit() {
        return borrowLimit;
    }

    public void setBorrowLimit(int borrowLimit) {
        this.borrowLimit = borrowLimit;
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", password='" + password + '\'' +
                ", studentName='" + studentName + '\'' +
                ", gender='" + gender + '\'' +
                ", faculty='" + faculty + '\'' +
                ", year=" + year +
                ", telephone='" + telephone + '\'' +
                ", borrowTimes=" + borrowTimes +
                ", lateReturnTimes=" + lateReturnTimes +
                ", creditValue=" + creditValue +
                ", creditRating='" + creditRating + '\'' +
                ", borrowLimit=" + borrowLimit +
                '}';
    }

    public int getBorrowedBookNum() {
        return borrowedBookNum;
    }

    public void setBorrowedBookNum(int borrowedBookNum) {
        this.borrowedBookNum = borrowedBookNum;
    }
}
