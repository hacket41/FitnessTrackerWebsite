package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import com.fitnesstracker.service.*;
import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.util.PasswordUtil;
import com.fitnesstracker.util.RedirectionUtil;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RegisterService registerService;
	private RedirectionUtil redirectionUtil;

	public void init() throws ServletException {
		this.registerService = new RegisterService();
		this.redirectionUtil = new RedirectionUtil();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			UserModel userModel = extractUserModel(request, response);
			if (userModel == null) return;

			Boolean isAdded = registerService.addUser(userModel);

			if (isAdded == null) {
				handleError(request, response, "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				handleSuccess(request, response, "Account created successfully", "/WEB-INF/pages/login.jsp");
			} else {
				handleError(request, response, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			handleError(request, response, "An unexpected error occurred. Please try again later!");
			e.printStackTrace();
		}
	}
	
	
	//method for receiving data from the register form.
	private UserModel extractUserModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String fName = request.getParameter("firstName");
		String lName = request.getParameter("lastName");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String birthdayStr = request.getParameter("birthday");
		String password = request.getParameter("password");
		String retypepassword = request.getParameter("retypePassword");

		// Validate required fields
		if (fName == null || lName == null || userName == null || email == null || birthdayStr == null || password == null) {
			redirectionUtil.setMsgAndRedirect(request, response, "error",
					"All fields are required!", RedirectionUtil.registerUrl);
			return null;
		}

		if (password == null || !password.equals(retypepassword)) {
			redirectionUtil.setMsgAndRedirect(request, response, "error",
					"Please correct your password & retype-password!", RedirectionUtil.registerUrl);
			return null;
		}

		// Parse birthday with proper error handling
		LocalDate birthDay;
		try {
			birthDay = LocalDate.parse(birthdayStr);
		} catch (Exception e) {
			redirectionUtil.setMsgAndRedirect(request, response, "error",
					"Invalid date format. Please use YYYY-MM-DD format.", RedirectionUtil.registerUrl);
			return null;
		}

		password = PasswordUtil.encrypt(userName, password);
		
		// Create and populate UserModel
		UserModel userModel = new UserModel();
		userModel.setF_name(fName);
		userModel.setL_name(lName);
		userModel.setUsername(userName);
		userModel.setEmail(email);
		userModel.setBirthday(birthDay);
		userModel.setPassword(password);
		
		return userModel;
	}

	private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
			throws ServletException, IOException {
		req.setAttribute("success", message);
		req.getRequestDispatcher(redirectPage).forward(req, resp);
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}

