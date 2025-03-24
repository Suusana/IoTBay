package com.enums;

public enum OrderStatus {
    PENDING(0, "Pending"),
    CONFIRMED(1, "Confirmed"),
    SHIPPED(2, "Shipped"),
    DELIVERED(3, "Delivered"),
    CANCELLED(4, "Cancelled");

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