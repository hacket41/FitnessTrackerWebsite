package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class HomeController
 * 
 * Handles requests to the home page of the application.
 * Forwards to the home.jsp view for both GET and POST requests.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home", "/" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Handles GET requests by forwarding to the home JSP page.
     * 
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an input or output error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to home.jsp page inside WEB-INF
        request.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(request, response);    
    }

    /**
     * Handles POST requests by delegating to doGet method.
     * 
     * @param request  HttpServletRequest object containing client request
     * @param response HttpServletResponse object for sending response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an input or output error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegate POST request handling to doGet
        doGet(request, response);
    }

}
