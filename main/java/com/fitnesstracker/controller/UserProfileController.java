package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;

import com.fitnesstracker.util.ImageUtil;

@WebServlet(asyncSupported = true, urlPatterns = { "/userprofile" })
@MultipartConfig
public class UserProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ImageUtil imageUtil = new ImageUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
                request.setAttribute("message", "Image uploaded successfully.");
            } else {
                request.setAttribute("message", "Image upload failed.");
            }
        }

        request.getRequestDispatcher("/WEB-INF/pages/userprofile.jsp").forward(request, response);
    }
}