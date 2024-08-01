package com.suyihang.entity;

public class Admin {
    private int adminId;
    private String account;
    private String password;
    private String adminName;
    private String telephone;

    // 无参构造函数
    public Admin() {
    }

    // 带参构造函数
    public Admin(int adminId, String account, String password, String adminName, String telephone) {
        this.adminId = adminId;
        this.account = account;
        this.password = password;
        this.adminName = adminName;
        this.telephone = telephone;
    }

    // Getter和Setter方法
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", adminName='" + adminName + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
