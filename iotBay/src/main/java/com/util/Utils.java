package com.util;

import java.util.HashMap;
import java.util.Map;

public final class Utils {

    private Utils() {};

    // capitalise first letter of input string
    public static String capitaliseFirst(String lowercaseInput) {
        return lowercaseInput.substring(0, 1).toUpperCase() + lowercaseInput.substring(1);
    }

    // formats phone number into 0[3] [3] [3]
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

    // validates each input in the registration and edit details forms and returns all errors
    public static Map<String, String> validateUserInput(String firstName, String lastName, String email, String password,
                                                        String confirmPassword, Long phoneNumber, String state, String city,
                                                        Integer postcode, String country, String address, boolean isEdit) {
        Map<String, String> errors = new HashMap<>();

        if (firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "First name is required");
        } else if (!firstName.matches("^[A-Za-z'\\-\\s]+$")) {
            errors.put("firstName", "First name is in invalid format");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "Last name is required");
        } else if (!lastName.matches("^[A-Za-z'\\-\\s]+$")) {
            errors.put("lastName", "Last name is in invalid format");
        }

        if (email == null) {
            errors.put("email", "Email is required");
        } else if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
            errors.put("email", "Email is in invalid format, must contain '@' and domain name");
        }

        boolean passwordProvided = password != null && !password.trim().isEmpty();
        boolean confirmPasswordProvided = confirmPassword != null && !confirmPassword.trim().isEmpty();

        // password input required while registering but only needed in editing if updating password
        if (!isEdit) {
            if (!passwordProvided || !confirmPasswordProvided) {
                errors.put("password", "Both password fields must be filled");
            } else if (!password.equals(confirmPassword)) {
                errors.put("password", "Passwords do not match");
            } else if (password.length() < 6) {
                errors.put("password", "Password length must be at least 6 characters");
            }
        } else if (passwordProvided || confirmPasswordProvided) {
            if (!passwordProvided || !confirmPasswordProvided) {
                errors.put("password", "Both password fields must be filled");
            } else if (!password.equals(confirmPassword)) {
                errors.put("password", "Passwords do not match");
            } else if (password.length() < 6) {
                errors.put("password", "Password length must be at least 6 characters");
            }
        }

        if (phoneNumber == null) {
            errors.put("phoneNumber", "Phone number is required");
        } else if (String.valueOf(phoneNumber).length() != 9) {
            errors.put("phoneNumber", "Phone number must be 10 digits including starting 0");
        }

        if (state == null || state.isEmpty()) {
            errors.put("state", "State is required");
        }

        if (city == null || city.isEmpty()) {
            errors.put("city", "City is required");
        } else if (!city.matches("^[A-Za-z'\\-\\s]+$")) {
            errors.put("city", "City must be letters only");
        }

        if (postcode == null) {
            errors.put("postcode", "Postcode is required");
        } else if (postcode < 1000 || postcode > 9999) {
            errors.put("postcode", "Postcode must be a 4 digit number");
        }

        if (country  == null || country.isEmpty()) {
            errors.put("country", "Country is required");
        } else if (!country.matches("^[A-Za-z'\\-\\s]+$")) {
            errors.put("country", "Country must be letters only");
        }

        if (address ==  null || address.isEmpty()) {
            errors.put("address", "Street is required");
        }

        return errors;
    }

}
