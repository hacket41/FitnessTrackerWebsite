package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/adminuser/delete")
public class DeleteUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");

        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);

                try (Connection conn = DBConfig.getDbConnection()) {
                    String sql = "DELETE FROM user WHERE user_id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, userId);

                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        request.setAttribute("message", "User deleted successfully.");
                    } else {
                        request.setAttribute("message", "User not found or couldn't be deleted.");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Error while deleting user.");
            }
        } else {
            request.setAttribute("message", "Invalid user ID.");
        }

        // Redirect back to user list
        response.sendRedirect(request.getContextPath() + "/adminusers");
    }
}
