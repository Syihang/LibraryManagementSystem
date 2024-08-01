package com.suyihang.entity;

import com.suyihang.database.DataSelect;
import com.suyihang.database.DataSelectBook;

import java.util.Date;

public class Lose {

    private String studentName;
    private String title;

    private int loseId;
    private String studentId;
    private String ISBN;
    private String status;
    private Date reportTime;
    private boolean isCompensation;
    private String remark;

    private String Name;

    public Lose() {
        // 默认构造方法
    }

    public Lose(String studentId, String ISBN, String status, Date reportTime, boolean isCompensation, String remark) {
        this.studentId = studentId;
        this.ISBN = ISBN;
        this.status = status;
        this.reportTime = reportTime;
        this.isCompensation = isCompensation;
        this.remark = remark;

        this.studentName = DataSelectBook.selectBookByISBN(ISBN).getTitle();
        this.Name = DataSelect.findStudentByID(studentId).getStudentName();
    }

    public int getLoseId() {
        return loseId;
    }

    public void setLoseId(int loseId) {
        this.loseId = loseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public boolean isCompensation() {
        return isCompensation;
    }

    public void setCompensation(boolean compensation) {
        isCompensation = compensation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Lose{" +
                "loseId=" + loseId +
                ", studentId='" + studentId + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", status='" + status + '\'' +
                ", reportTime=" + reportTime +
                ", isCompensation=" + isCompensation +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
