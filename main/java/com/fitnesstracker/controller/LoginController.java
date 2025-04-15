package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fitnesstracker.util.RedirectionUtil;
import com.fitnesstracker.util.ValidationUtil;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ValidationUtil validationUtil;
	private RedirectionUtil redirectionUtil;
    /**
     * @see HttpServlet#HttpServlet()
     */
	@Override
	public void init() throws ServletException {
		this.validationUtil = new ValidationUtil();
		this.redirectionUtil = new RedirectionUtil();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher(RedirectionUtil.loginUrl).forward(request,response);
	}
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		
		if(validationUtil.isNullOrEmpty("username") || validationUtil.isNullOrEmpty(password)) {
			redirectionUtil.setMsgAndRedirect(request, response, "error", "Please do leave the fields empty.", RedirectionUtil.loginUrl);
		}
		else {
			if(username.equals("user") && password.equals("password")){
				redirectionUtil.setMsgAndRedirect(request, response, "success", "Successfully Logged In!", RedirectionUtil.homeUrl);
			}
			else {
				redirectionUtil.setMsgAndRedirect(request, response, "error", "Either username or password is mistake.",
						RedirectionUtil.loginUrl);
			}
		}
	}

}
