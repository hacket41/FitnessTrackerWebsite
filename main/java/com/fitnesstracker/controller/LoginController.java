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

        if (!validationUtil.isNullOrEmpty(username) && !validationUtil.isNullOrEmpty(password)) {
            UserModel userModel = new UserModel(username, password);
            Boolean loginStatus = loginService.loginUser(userModel);

            if (loginStatus != null && loginStatus) {
                SessionUtil.setAttribute(req, "username", username);
                UserModel user = loginService.getFullUserByUsername(username);
                req.getSession().setAttribute("user", user);
                req.getSession().setAttribute("userId", user.getUserId()); // Set userId for MealController
                String role = loginService.getUserRole(username);

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
            redirectionUtil.setMsgAndRedirect(req, resp, "error", "Please fill all the fields!",
                    RedirectionUtil.loginUrl);
        }
    }

    private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
            throws ServletException, IOException {
        String errorMessage = (loginStatus == null)
                ? "Server is under maintenance. Please try again later."
                : "Details invalid. Please try again!";
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher(RedirectionUtil.loginUrl).forward(req, resp);
    }
}