package com.enums;

public enum Position {
    ADMIN("Admin"),
    STAFF("Staff"),
    MANAGER("Manager");

    private String name;

    Position(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
