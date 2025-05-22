package com.fitnesstracker.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.LoginService;
import com.fitnesstracker.util.CookiesUtil;
import com.fitnesstracker.util.SessionUtil;
import com.fitnesstracker.util.RedirectionUtil;
import com.fitnesstracker.util.ValidationUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginController
 * 
 * Handles user login requests, including validation, authentication,
 * session management, and role-based redirection.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ValidationUtil validationUtil;
    private RedirectionUtil redirectionUtil;
    private LoginService loginService;

    /**
     * Initializes required utility and service instances when the servlet is created.
     */
    @Override
    public void init() throws ServletException {
        this.validationUtil = new ValidationUtil();
        this.redirectionUtil = new RedirectionUtil();
        this.loginService = new LoginService();
    }

    /**
     * Handles GET requests by forwarding to the login page.
     * 
     * @param req  HttpServletRequest object containing the client request
     * @param resp HttpServletResponse object for sending the response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
    }

    /**
     * Handles POST requests to authenticate users.
     * Validates input, checks credentials, sets session and cookies,
     * tracks online users, and redirects based on user roles.
     * 
     * @param req  HttpServletRequest object containing client request
     * @param resp HttpServletResponse object for sending response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!validationUtil.isNullOrEmpty(username) && !validationUtil.isNullOrEmpty(password)) {
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            
            Boolean loginStatus = loginService.loginUser(userModel);

            if (loginStatus != null && loginStatus) {
                // Set session attributes for username and user details
                SessionUtil.setAttribute(req, "username", username);
                UserModel user = loginService.getFullUserByUsername(username);
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("userId", user.getUserId());

                String role = loginService.getUserRole(username); // Declare before using
                req.getSession().setAttribute("role", role);      // Now no error here

                // Track online users
                ServletContext context = getServletContext();
                Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");
                if (onlineUsers == null) {
                    onlineUsers = new HashSet<>();
                }
                onlineUsers.add(username);
                context.setAttribute("onlineUsers", onlineUsers);

                // Role-based redirection
                if ("admin".equalsIgnoreCase(role)) {
                    CookiesUtil.addCookie(resp, "role", "admin", 5 * 30);
                    resp.sendRedirect(req.getContextPath() + "/admin");
                } else {
                    CookiesUtil.addCookie(resp, "role", "user", 5 * 30);
                    resp.sendRedirect(req.getContextPath() + "/home");
                }

            } else {
                handleLoginFailure(req, resp, loginStatus);
            }
        } else {
            // Redirect with error message if username or password is missing
            redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please fill all the fields!",
                    RedirectionUtil.loginUrl);
        }
    }

    /**
     * Handles login failure scenarios by setting an appropriate error message
     * and forwarding back to the login page.
     * 
     * @param req         HttpServletRequest object
     * @param resp        HttpServletResponse object
     * @param loginStatus Boolean indicating the login status (null or false)
     * @throws ServletException if forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
            throws ServletException, IOException {
        String errorMessage = (loginStatus == null)
                ? "Server is under maintenance. Please try again later."
                : "Details invalid. Please try again!";
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
    }
}
