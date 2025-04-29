package com.fitnesstracker.controller;

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
import java.time.LocalDate;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.util.ImageUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/userprofile" })
@MultipartConfig
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ImageUtil imageUtil = new ImageUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserModel user = (UserModel) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
        }
    }

    

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract form fields
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String birthdayStr = request.getParameter("date");

        LocalDate birthday = birthdayStr != null && !birthdayStr.isEmpty()
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
                request.setAttribute("imagePath", imagePath);
            }
        }

        // Get logged-in user from session
        UserModel user = (UserModel) request.getSession().getAttribute("user");

        // Update user model
        user.setF_name(firstName);
        user.setL_name(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setBirthday(birthday);
        if (imagePath != null) {
            user.setImage_path(imagePath);
        }

        // Update user in DB directly here
        try (Connection conn = DBConfig.getDbConnection()) {
            String sql = "UPDATE user SET f_name=?, l_name=?, username=?, email=?, birthday=?, image_path=? WHERE user_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getF_name());
            stmt.setString(2, user.getL_name());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getEmail());
            stmt.setDate(5, java.sql.Date.valueOf(user.getBirthday()));
            stmt.setString(6, user.getImage_path());
            stmt.setInt(7, user.getUserId());

            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                request.setAttribute("message", "Profile updated successfully.");
            } else {
                request.setAttribute("message", "Failed to update profile.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating profile.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }
}
