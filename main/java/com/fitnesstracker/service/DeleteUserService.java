package com.fitnesstracker.service;

import com.fitnesstracker.config.DBConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/adminuser/delete")
public class DeleteUserService extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        String errorMessage = null;

        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);
                
                // Check if trying to delete self
                Integer currentUserId = (Integer) request.getSession().getAttribute("userId");
                if (currentUserId != null && currentUserId == userId) {
                    errorMessage = "You cannot delete your own account.";
                } else {
                    try (Connection conn = DBConfig.getDbConnection()) {
                        // First check if user exists
                        String checkSql = "SELECT COUNT(*) FROM user WHERE user_id = ?";
                        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                        checkStmt.setInt(1, userId);
                        ResultSet rs = checkStmt.executeQuery();
                        
                        if (rs.next() && rs.getInt(1) > 0) {
                            // Start transaction
                            conn.setAutoCommit(false);
                            try {
                                // Delete related records first
                                // Delete from favorite_meals
                                String deleteFavoritesSql = "DELETE FROM favorite_meals WHERE user_id = ?";
                                PreparedStatement deleteFavoritesStmt = conn.prepareStatement(deleteFavoritesSql);
                                deleteFavoritesStmt.setInt(1, userId);
                                deleteFavoritesStmt.executeUpdate();

                                // Delete from progress
                                String deleteProgressSql = "DELETE FROM progress WHERE user_id = ?";
                                PreparedStatement deleteProgressStmt = conn.prepareStatement(deleteProgressSql);
                                deleteProgressStmt.setInt(1, userId);
                                deleteProgressStmt.executeUpdate();

                                // Delete from water_intake
                                String deleteWaterSql = "DELETE FROM water_intake WHERE user_id = ?";
                                PreparedStatement deleteWaterStmt = conn.prepareStatement(deleteWaterSql);
                                deleteWaterStmt.setInt(1, userId);
                                deleteWaterStmt.executeUpdate();

                                // Finally delete the user
                                String deleteUserSql = "DELETE FROM user WHERE user_id = ?";
                                PreparedStatement deleteUserStmt = conn.prepareStatement(deleteUserSql);
                                deleteUserStmt.setInt(1, userId);
                                
                                int rows = deleteUserStmt.executeUpdate();
                                if (rows > 0) {
                                    conn.commit();
                                    request.getSession().setAttribute("successMessage", "User deleted successfully.");
                                } else {
                                    conn.rollback();
                                    errorMessage = "Failed to delete user.";
                                }
                            } catch (Exception e) {
                                conn.rollback();
                                throw e;
                            } finally {
                                conn.setAutoCommit(true);
                            }
                        } else {
                            errorMessage = "User not found.";
                        }
                    }
                }
            } catch (NumberFormatException e) {
                errorMessage = "Invalid user ID format.";
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = "An error occurred while deleting the user: " + e.getMessage();
            }
        } else {
            errorMessage = "No user ID provided.";
        }

        if (errorMessage != null) {
            request.getSession().setAttribute("errorMessage", errorMessage);
        }
        
        response.sendRedirect(request.getContextPath() + "/adminusers");
    }
}
