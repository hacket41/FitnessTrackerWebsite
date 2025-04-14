
package com.fitnesstracker.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import jakarta.servlet.http.Part;


public class ValidationUtil {

	// 1. Validate if a field is null or empty
	public boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	// 2. Validate if a string contains only letters
	public boolean isAlphabetic(String value) {
		return !isNullOrEmpty(value) && value.matches("^[a-zA-Z]+$"); //
	}

	// 3. Validate if a string starts with a letter and is composed of letters and
	// numbers
	public boolean isAlphanumericStartingWithLetter(String value) {
		return !isNullOrEmpty(value) && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
	}

	

	// 5. Validate if a string is a valid email address
	public boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return !isNullOrEmpty(email) && Pattern.matches(emailRegex, email);
	}

	
	// 7. Validate if a password is composed of at least 1 capital letter, 1 number,
	// and 1 symbol
	public boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		return !isNullOrEmpty(password) && password.matches(passwordRegex);
	}

	// 8. Validate if a Part's file extension matches with image extensions (jpg,
	// jpeg, png, gif)
	

	// 9. Validate if password and retype password match
	public boolean doPasswordsMatch(String password, String retypePassword) {
		return !isNullOrEmpty(password) && !isNullOrEmpty(retypePassword) && password.equals(retypePassword);
	}

	// 10. Validate if the date of birth is at least 16 years before today

}