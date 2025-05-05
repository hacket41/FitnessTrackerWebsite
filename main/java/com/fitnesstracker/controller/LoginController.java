package com.fitnesstracker.controller;

import java.io.IOException;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.LoginService;
import com.fitnesstracker.util.CookiesUtil;
import com.fitnesstracker.util.SessionUtil;
import com.fitnesstracker.util.RedirectionUtil;
import com.fitnesstracker.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ValidationUtil validationUtil;
	private RedirectionUtil redirectionUtil;
	private LoginService loginService;

	@Override
	public void init() throws ServletException {
		this.validationUtil = new ValidationUtil();
		this.redirectionUtil = new RedirectionUtil();
		this.loginService = new LoginService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		// Corrected input validation (use actual values, not strings)
		if (!validationUtil.isNullOrEmpty(username) && !validationUtil.isNullOrEmpty(password)) {

			UserModel userModel = new UserModel(username, password);
			Boolean loginStatus = loginService.loginUser(userModel);

			if (loginStatus != null && loginStatus) {
				SessionUtil.setAttribute(req, "username", username);

				if ("admin".equals(username)) {
					CookiesUtil.addCookie(resp, "role", "admin", 5 * 30);
					resp.sendRedirect(req.getContextPath() + "/admin");
				} else {
					CookiesUtil.addCookie(resp, "role", "student", 5 * 30);
					resp.sendRedirect(req.getContextPath() + "/home");
				}
			} else {
				handleLoginFailure(req, resp, loginStatus);
			}
		} else {
			redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please fill all the fields!",
					RedirectionUtil.loginUrl);
		}
	}

	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         			HttpServletRequest object
	 * @param resp        			HttpServletResponse object
	 * @param loginStatus 			Boolean indicating the login status
	 * @throws ServletException 	if a servlet-specific error occurs
	 * @throws IOException      	if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}

		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
	}
}
