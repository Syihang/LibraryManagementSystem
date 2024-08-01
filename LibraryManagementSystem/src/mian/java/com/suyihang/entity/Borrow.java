package com.suyihang.entity;

//import java.util.Date;
import com.suyihang.database.DataSelect;
import com.suyihang.database.DataSelectBook;
import com.suyihang.util.DateUtil;

import java.sql.Date;

public class Borrow {
    private int borrowId;
    private String studentId;
    private String ISBN;
    private Date borrowTime;
    private Date returnTime;
    private Date actualReturnTime;

    private String title;
    private String author;
    private String type;
    private String days;  // 剩余时间
    private String name;

    // Constructors
    public Borrow() {}

    public Borrow(int borrowId, String studentId, String ISBN, Date borrowTime, Date returnTime, Date actualReturnTime) {
        this.borrowId = borrowId;
        this.studentId = studentId;
        this.ISBN = ISBN;
        this.borrowTime = borrowTime;
        this.returnTime = returnTime;
        this.actualReturnTime = actualReturnTime;

        Book book = DataSelectBook.selectBookByISBN(ISBN);

        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.type = book.getTypeName();

        if (actualReturnTime != null) {
            this.days = "已归还";
        }else if (DateUtil.timeCompare(DateUtil.getCurrentTime(), returnTime) != 1) {
            this.days = String.valueOf(DateUtil.calculateDateDifference(DateUtil.getCurrentTime(), returnTime));
        } else {
            this.days = "已逾期";
        }

        this.name = DataSelect.findStudentByID(studentId).getStudentName();

    }

    // Getters and Setters
    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
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

    public Date getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Date getActualReturnTime() {
        return actualReturnTime;
    }

    public void setActualReturnTime(Date actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    // toString method
    @Override
    public String toString() {
        return "Borrow{" +
                "borrowId=" + borrowId +
                ", studentId='" + studentId + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", borrowTime=" + borrowTime +
                ", returnTime=" + returnTime +
                ", actualReturnTime=" + actualReturnTime +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
