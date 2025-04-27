package com.enums;

public enum State {
    // All the states in Australia
    NSW("NSW"),
    QLD("QLD"),
    SA("SA"),
    TAS("TAS"),
    VIC("VIC"),
    WA("WA"),
    ACT("ACT"),
    NT("NT");

    private String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}