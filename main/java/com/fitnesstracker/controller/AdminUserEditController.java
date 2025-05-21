package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.UserFunctionsService;

/**
 * Servlet implementation for editing user details in the admin panel.
 * Handles loading a specific user by ID to populate the edit form.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminedit" })
public class AdminUserEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserFunctionsService userFunctions = new UserFunctionsService();

    /**
     * Handles GET requests to fetch user details by ID and forward to the edit page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        try {
            // Retrieve user details from the database using the service layer
            UserModel user = userFunctions.getUserById(userId);
            
            // Set the user object in the request to be used in the JSP
            request.setAttribute("editUser", user);
            
            // Forward to the JSP for editing user information
            request.getRequestDispatcher("/WEB-INF/pages/admin_edit_user.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // If an exception occurs, send HTTP 500 Internal Server Error
            response.sendError(500);
        }
    }

    /**
     * Handles POST requests (not implemented yet).
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Placeholder for handling form submission for editing a user (future implementation)
    }
}
