package com.util;

public final class Utils {

    private Utils() {};

    public static String capitaliseFirst(String lowercaseInput) {
        return lowercaseInput.substring(0, 1).toUpperCase() + lowercaseInput.substring(1);
    }

}
