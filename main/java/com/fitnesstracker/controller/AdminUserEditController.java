package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.UserFunctions;

/**
 * Servlet implementation class AdminUserEditController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminedit" })
public class AdminUserEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserFunctions userFunctions = new UserFunctions();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        try {
            UserModel user = userFunctions.getUserById(userId);
            request.setAttribute("editUser", user);
            request.getRequestDispatcher("/WEB-INF/pages/admin_edit_user.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }
}
