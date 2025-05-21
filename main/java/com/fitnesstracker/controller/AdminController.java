package com.fitnesstracker.controller;

import com.fitnesstracker.service.UserFunctionsService;
import com.fitnesstracker.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation for the admin dashboard.
 * Handles retrieving user data and forwarding it to the admin JSP view.
 */
@WebServlet("/admin")
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests for the admin dashboard.
     * Retrieves a list of all users and total user count from the service layer,
     * sets them as request attributes, and forwards to the admin JSP.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserFunctionsService userFunctions = new UserFunctionsService();
            List<UserModel> userList = userFunctions.getAllUsers();
            int userCount = userFunctions.getUserCount();
            request.setAttribute("userList", userList);
            request.setAttribute("userCount", userCount);
            request.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle exception properly
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles POST requests by delegating to the doGet method.
     * This allows the same behavior for both GET and POST requests to this endpoint.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
