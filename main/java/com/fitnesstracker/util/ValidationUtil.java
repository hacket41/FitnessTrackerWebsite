package com.fitnesstracker.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    /**
     * Check if a string is null or empty after trimming.
     *
     * @param value the string to check
     * @return true if null or empty, false otherwise
     */
    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Check if a string contains only alphabetic letters.
     *
     * @param value the string to check
     * @return true if only letters, false otherwise
     */
    public boolean isAlphabetic(String value) {
        return !isNullOrEmpty(value) && value.matches("^[a-zA-Z]+$");
    }

    /**
     * Check if a string starts with a letter and contains only letters and digits.
     *
     * @param value the string to check
     * @return true if it starts with a letter and is alphanumeric, false otherwise
     */
    public boolean isAlphanumericStartingWithLetter(String value) {
        return !isNullOrEmpty(value) && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    /**
     * Validate if the input is a valid email format.
     *
     * @param email the email string to check
     * @return true if valid email, false otherwise
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return !isNullOrEmpty(email) && Pattern.matches(emailRegex, email);
    }

    /**
     * Validate if a password has at least one uppercase letter, one digit, one special symbol, and minimum length of 8.
     *
     * @param password the password string to check
     * @return true if password meets criteria, false otherwise
     */
    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return !isNullOrEmpty(password) && password.matches(passwordRegex);
    }

    /**
     * Check if two password strings match exactly.
     *
     * @param password the first password
     * @param retypePassword the second password to compare
     * @return true if both match, false otherwise
     */
    public boolean doPasswordsMatch(String password, String retypePassword) {
        return !isNullOrEmpty(password) && !isNullOrEmpty(retypePassword) && password.equals(retypePassword);
    }
}
