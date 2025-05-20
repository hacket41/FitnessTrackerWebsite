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

@WebServlet(asyncSupported = true, urlPatterns = { "/adminusers" })
public class AdminUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SearchService searchService;

    @Override
    public void init() throws ServletException {
        System.out.println("AdminUserController initialized");
        searchService = new SearchService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get search query parameter from request
            String searchQuery = request.getParameter("search");
            
            // Search for users based on the query or get all users if no query
            List<UserModel> userList = searchService.searchUsersByUsername(searchQuery);
            
            // Get list of online users (this will be used for status display)
            List<String> onlineUsers = new ArrayList<>();
            try {
                onlineUsers = searchService.getOnlineUsers();
            } catch (Exception e) {
                System.err.println("Error getting online users: " + e.getMessage());
                // Continue with empty list if this fails
            }
            
            // Set attributes for the JSP
            request.setAttribute("userList", userList);
            request.setAttribute("onlineUsers", onlineUsers);
            request.setAttribute("searchQuery", searchQuery != null ? searchQuery : "");
            
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error in AdminUserController: " + e.getMessage());
            e.printStackTrace();
            
            // If an error occurs, set empty lists
            request.setAttribute("userList", new ArrayList<UserModel>());
            request.setAttribute("onlineUsers", new ArrayList<String>());
            request.setAttribute("searchQuery", "");
            
            // You might want to add an error message
            request.setAttribute("errorMessage", "An error occurred while processing your request: " + e.getMessage());
        }
        
        // Forward to the JSP
        request.getRequestDispatcher("/WEB-INF/pages/adminuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}