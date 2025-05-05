package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/userprofile" })
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optionally load user info from session or database to show in form
        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form data
        String f_name = request.getParameter("f_name");
        String l_name = request.getParameter("l_name");
        String email = request.getParameter("email");

        // Get encrypted username from session
        String encryptedUsername = (String) request.getSession().getAttribute("enc_username");

        boolean updated = false;

        String sql = "UPDATE users SET f_name = ?, l_name = ?, email = ? WHERE username = ?";

        try (Connection conn = DBConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f_name);
            stmt.setString(2, l_name);
            stmt.setString(3, email);
            stmt.setString(4, encryptedUsername); // use the encrypted username

            int rowsAffected = stmt.executeUpdate();
            updated = rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Set attributes and forward to JSP
        UserModel user = new UserModel();
        user.setF_name(f_name);
        user.setL_name(l_name);
        user.setEmail(email);
        user.setUsername(encryptedUsername); // Optional: only if needed in view

        request.setAttribute("user", user);
        request.setAttribute("message", updated ? "Profile updated successfully." : "Failed to update profile.");
        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }
}
