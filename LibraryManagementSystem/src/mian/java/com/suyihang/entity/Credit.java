package com.suyihang.entity;

public class Credit {
    private int borrowTimes;      // 借书次数
    private int lateReturnTimes;  // 逾期次数
    private double creditValue;   // 信用值
    private String creditRating;  // 信用等级
    private int borrowLimit;      // 借书限制

    // 构造方法
    public Credit() {}

    public Credit(int borrowTimes, int lateReturnTimes, double creditValue, String creditRating, int borrowLimit) {
        this.borrowTimes = borrowTimes;
        this.lateReturnTimes = lateReturnTimes;
        this.creditValue = creditValue;
        this.creditRating = creditRating;
        this.borrowLimit = borrowLimit;
    }

    // Getter 和 Setter 方法
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
        return "Credit{" +
                "borrowTimes=" + borrowTimes +
                ", lateReturnTimes=" + lateReturnTimes +
                ", creditValue=" + creditValue +
                ", creditRating='" + creditRating + '\'' +
                ", borrowLimit=" + borrowLimit +
                '}';
    }
}
