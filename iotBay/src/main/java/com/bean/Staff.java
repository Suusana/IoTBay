package com.bean;
import java.io.Serializable;
public class Staff implements Serializable {
    private Integer staffId;
    private String staffName;
    private String password;
    private Long phoneNum;

    public Staff() {
    }

    public Staff(Integer staffId, String staffName, String password, Long phoneNum) {
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

    public Long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(Long phoneNum) {
        this.phoneNum = phoneNum;
    }
}