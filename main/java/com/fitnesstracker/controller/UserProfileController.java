package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.UserFunctions;
import com.fitnesstracker.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(asyncSupported = true, urlPatterns = { "/userprofile" })
@MultipartConfig
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ImageUtil imageUtil = new ImageUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        UserModel user = null;

        if (userIdParam != null && !userIdParam.isEmpty()) {
            // Admin is editing another user's profile
            int userId = Integer.parseInt(userIdParam);

            try (Connection conn = DBConfig.getDbConnection()) {
                String sql = "SELECT * FROM user WHERE user_id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    user = new UserModel();
                    user.setId(rs.getInt("user_id"));
                    user.setF_name(rs.getString("f_name"));
                    user.setL_name(rs.getString("l_name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                    user.setImage_path(rs.getString("image_path"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Normal user accessing their own profile
            user = (UserModel) request.getSession().getAttribute("user");

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        UserModel user = null;

        try {
            if (userIdParam != null) {
                int userId = Integer.parseInt(userIdParam);
                user = new UserFunctions().getUserById(userId);
            } else {
                user = (UserModel) request.getSession().getAttribute("user");
            }

            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Extract form fields
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String birthdayStr = request.getParameter("date");

            LocalDate birthday = (birthdayStr != null && !birthdayStr.isEmpty())
                    ? LocalDate.parse(birthdayStr)
                    : null;

            Part image = request.getPart("image");
            String imagePath = null;

            if (image != null && image.getSize() > 0) {
                String folder = "profile";
                String savePath = request.getServletContext().getRealPath("/resources/images/" + folder);
                boolean uploaded = imageUtil.uploadImage(image, savePath);

                if (uploaded) {
                    String imageName = imageUtil.getImageNameFromPart(image);
                    imagePath = "resources/images/" + folder + "/" + imageName;
                }
            }

            // Update fields
            user.setF_name(firstName);
            user.setL_name(lastName);
            user.setEmail(email);
            user.setBirthday(birthday);
            if (imagePath != null) {
                user.setImage_path(imagePath);
            }

            try (Connection conn = DBConfig.getDbConnection()) {
                String sql = "UPDATE user SET f_name=?, l_name=?, email=?, birthday=?, image_path=? WHERE user_id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, user.getF_name());
                stmt.setString(2, user.getL_name());
                stmt.setString(3, user.getEmail());
                if (user.getBirthday() != null) {
                    stmt.setDate(4, java.sql.Date.valueOf(user.getBirthday()));
                } else {
                    stmt.setNull(4, java.sql.Types.DATE);
                }
                stmt.setString(5, user.getImage_path());
                stmt.setInt(6, user.getUserId());

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    request.setAttribute("message", "Profile updated successfully.");
                } else {
                    request.setAttribute("message", "Failed to update profile.");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("message", "Error updating profile.");
            }

            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid form data.");
        }
    }
}
