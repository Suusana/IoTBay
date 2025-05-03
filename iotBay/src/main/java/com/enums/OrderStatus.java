package com.enums;

public enum OrderStatus {
    Pending(0, "Pending"),
    Confirmed(1, "Confirmed"),
    Shipped(2, "Shipped"),
    Delivered(3, "Delivered"),
    Cancelled(4, "Cancelled"),
    Saved(5, "Saved");

    private int code;
    private String status;

    OrderStatus(int code, String status) {
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