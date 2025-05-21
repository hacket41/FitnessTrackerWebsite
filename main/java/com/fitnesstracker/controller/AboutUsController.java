package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class AboutUsController
 * Handles the About Us page functionality
 */
@WebServlet("/about")
public class AboutUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public AboutUsController() {
        super();
    }

    /**
     * Handles GET requests for the About Us page.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unused")
        Integer userId = (Integer) session.getAttribute("userId");

        // Forward to the about.jsp page
        request.getRequestDispatcher("/WEB-INF/pages/about.jsp").forward(request, response);
    }

    /**
     * Handles POST requests by delegating to the doGet method.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}  
