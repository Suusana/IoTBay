package com.enums;

public enum Title {
    // all actions of system application logs
    USER_REGISTER(100, "User registered"),
    USER_LOGIN(101, " User login"),
    USER_LOGOUT(102, "User logout"),
    USER_UPDATE_PROFILE(103, "User updated profile"),
    USER_PLACE_ORDER(104, "User placed an order"),
    USER_CANCEL(105, "User cancelled"),

    STAFF_LOGIN(200, "Staff login"),
    STAFF_LOGOUT(201, "Staff logout"),
    STAFF_ADD_PRODUCT(202, "Staff added"),
    STAFF_UPDATE(203, "Staff updated"),
    STAFF_DELETE(204, "Staff deleted"),
    STAFF_MANAGE(205, "Staff managed");

    private int code;
    private String title;

    Title(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}