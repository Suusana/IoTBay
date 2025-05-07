package com.util;

public final class Utils {

    private Utils() {};

    public static String capitaliseFirst(String lowercaseInput) {
        return lowercaseInput.substring(0, 1).toUpperCase() + lowercaseInput.substring(1);
    }

    public static String formatPhoneNumber(Long phoneNo) {
        if (phoneNo == null) return "";
        String phoneAsString  = String.valueOf(phoneNo);

        // Add 0 at start of phone number
        if (phoneAsString.length() == 9) {
            phoneAsString = "0" +  phoneAsString;
        }

        if (phoneAsString.length() != 10) return phoneAsString;
        return phoneAsString.replaceFirst("(\\d{4})(\\d{3})(\\d{3})", "$1 $2 $3");
    }

}
