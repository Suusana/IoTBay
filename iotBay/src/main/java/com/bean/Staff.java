package com.bean;
import java.io.Serializable;
public class Staff implements Serializable {
    private Integer staffId;
    private String staffName;
    private String password;
    private Integer phoneNum;
    private String email;
    private String position;
    private String Status;
    private String address;
    private String city;
    private String postcode;
    private String state;
    private String country;

    public Staff() {
    }

    public Staff(Integer staffId, String staffName, String password, Integer phoneNum,
                 String email, String position, String status, String address, String city,
                 String postcode, String state, String country) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.password = password;
        this.phoneNum = phoneNum;
        this.email = email;
        this.position = position;
        Status = status;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.state = state;
        this.country = country;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNum=" + phoneNum +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", Status='" + Status + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}