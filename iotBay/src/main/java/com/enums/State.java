package com.enums;

public enum State {
    // All the states in Australia
    NSW("New South Wales"),
    QLD("Queensland"),
    SA("South Australia"),
    TAS("Tasmania"),
    VIC("Victoria"),
    WA("Western Australia"),
    ACT("Australian Capital Territory"),
    NT("Northern Territory");

    private String state;

    State(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}