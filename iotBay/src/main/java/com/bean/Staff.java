package com.bean;

public class Staff {
    private Integer staffId;
    private String staffName;
    private String password;
    private Integer phoneNum;

    public Staff() {
    }

    public Staff(Integer staffId, String staffName, String password, Integer phoneNum) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.password = password;
        this.phoneNum = phoneNum;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Integer phoneNum) {
        this.phoneNum = phoneNum;
    }
}