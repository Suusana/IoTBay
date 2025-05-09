package com.enums;

public enum UserType {
    // get type of customer
    COMPANY("Company"),
    INDIVIDUAL("Individual");

    private String name;

    UserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
