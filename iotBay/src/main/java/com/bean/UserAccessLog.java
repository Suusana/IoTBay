package com.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserAccessLog implements Serializable {

    private int userAccessLogId; // auto increment in database
    private int userId;
    private String userType;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    public UserAccessLog() {}

    // Create on login - no logout yet
    public UserAccessLog(int userId, String userType, LocalDateTime loginTime) {
        this.userId = userId;
        this.userType = userType;
        this.loginTime = loginTime;
    }

    // Load from database
    public UserAccessLog(int userId, String userType, LocalDateTime loginTime, LocalDateTime logoutTime) {
        this.userId = userId;
        this.userType = userType;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public void setUserAccessLogId(int userAccessLogId) {
        this.userAccessLogId = userAccessLogId;
    }

    public int getUserAccessLogId() {
        return userAccessLogId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    // Methods to format login and logout time for display
    public String getLoginTimeFormatted() {
        if (loginTime == null) { return null; }

//        ZonedDateTime zonedLoginTime = loginTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return loginTime.format(formatter);
    }

    public String getLogoutTimeFormatted() {
        if (logoutTime == null) { return null; }

//        ZonedDateTime zonedLogoutTime = logoutTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return logoutTime.format(formatter);
    }
}
