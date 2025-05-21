package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fitnesstracker.util.CookiesUtil;
import com.fitnesstracker.util.SessionUtil;
import java.io.IOException;

/**
 * Servlet implementation class LogoutController
 * 
 * Handles user logout requests by deleting role cookies,
 * invalidating the HTTP session, and redirecting to the home page.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles POST requests for logout by delegating to logoutUser method.
     *
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutUser(request, response);
    }

    /**
     * Handles GET requests for logout by delegating to logoutUser method.
     *
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logoutUser(request, response);
    }

    /**
     * Performs logout by deleting the "role" cookie, invalidating the session,
     * and redirecting the user to the home page.
     *
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response
     * @throws IOException if an I/O error occurs during redirection
     */
    private void logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CookiesUtil.deleteCookie(response, "role");
        SessionUtil.invalidateSession(request);
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
