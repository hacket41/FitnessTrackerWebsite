package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
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
import java.time.format.DateTimeParseException;

/**
 * Servlet controller responsible for handling user profile operations such as
 * retrieving and updating profile information and handling image uploads.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/userprofile" })
@MultipartConfig
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ImageUtil imageUtil = new ImageUtil();

    /**
     * Handles GET requests to fetch user profile data from the database
     * or from session if no user ID is provided in the request.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userId");
        UserModel user = null;

        if (userIdParam != null && !userIdParam.isEmpty()) {
            try {
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            user = (UserModel) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to update user profile information including
     * name, email, birthday, and profile image.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            UserModel user = new UserModel();
            user.setUserId(userId);
            user.setF_name(request.getParameter("firstName"));
            user.setL_name(request.getParameter("lastName"));
            user.setEmail(request.getParameter("email"));

            String birthdayStr = request.getParameter("birthday");
            if (birthdayStr != null && !birthdayStr.trim().isEmpty()) {
                try {
                    user.setBirthday(LocalDate.parse(birthdayStr));
                } catch (DateTimeParseException e) {
                    request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD format.");
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
                    return;
                }
            }

            String imagePath = handleImageUpload(request);
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
                    request.setAttribute("success", "Profile updated successfully.");
                } else {
                    request.setAttribute("error", "Failed to update profile.");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                request.setAttribute("error", "Database error. Try again later.");
            }

            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred.");
            request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
        }
    }

    /**
     * Handles image upload from the form. Saves the image to the server
     * and returns the relative path to be stored in the database.
     *
     * @param request the HttpServletRequest containing the image part
     * @return the relative path of the uploaded image or null if upload fails
     * @throws IOException
     * @throws ServletException
     */
    private String handleImageUpload(HttpServletRequest request) throws IOException, ServletException {
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

        return imagePath;
    }
}
