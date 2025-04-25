package com.enums;

public enum Position {
    ADMIN("Admin"),
    STAFF("Staff");

    private String position;

    Position(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
