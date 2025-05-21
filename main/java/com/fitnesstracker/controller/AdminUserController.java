package com.fitnesstracker.controller;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.SearchService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation for handling user management in the admin dashboard.
 * Supports searching for users and displaying their online status.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminusers" })
public class AdminUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SearchService searchService;

    /**
     * Initializes the servlet and instantiates the SearchService.
     */
    @Override
    public void init() throws ServletException {
        System.out.println("AdminUserController initialized");
        searchService = new SearchService();
    }

    /**
     * Handles GET requests to display the list of users (optionally filtered by search query).
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get search query parameter from request
            String searchQuery = request.getParameter("search");
            
            // Search for users based on the query or get all users if no query is provided
            List<UserModel> userList = searchService.searchUsersByUsername(searchQuery);
            
            // Attempt to get the list of online users for display
            List<String> onlineUsers = new ArrayList<>();
            try {
                onlineUsers = searchService.getOnlineUsers();
            } catch (Exception e) {
                System.err.println("Error getting online users: " + e.getMessage());
                // Fallback: continue with an empty list if online status retrieval fails
            }
            
            // Set data as request attributes for use in the JSP
            request.setAttribute("userList", userList);
            request.setAttribute("onlineUsers", onlineUsers);
            request.setAttribute("searchQuery", searchQuery != null ? searchQuery : "");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in AdminUserController: " + e.getMessage());
            e.printStackTrace();
            
            // On error, fallback to empty data and inform the view
            request.setAttribute("userList", new ArrayList<UserModel>());
            request.setAttribute("onlineUsers", new ArrayList<String>());
            request.setAttribute("searchQuery", "");
            request.setAttribute("errorMessage", "An error occurred while processing your request: " + e.getMessage());
        }
        
        // Forward the request to the JSP for rendering the admin user management page
        request.getRequestDispatcher("/WEB-INF/pages/adminuser.jsp").forward(request, response);
    }

    /**
     * Handles POST requests by delegating them to the doGet method.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
