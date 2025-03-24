package com.enums;

public enum Status {
    // Status of customers

    REGISTERED(0,"Registered"),
    GUEST(1,"Guest");

    private int code;
    private String status;

    Status(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}