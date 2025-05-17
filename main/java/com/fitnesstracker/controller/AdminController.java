package com.fitnesstracker.controller;

import com.fitnesstracker.service.UserFunctions;
import com.fitnesstracker.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserFunctions userFunctions = new UserFunctions();
            List<UserModel> userList = userFunctions.getAllUsers();
            int userCount = userFunctions.getUserCount();
            request.setAttribute("userList", userList);
            request.setAttribute("userCount", userCount);
            request.getRequestDispatcher("/WEB-INF/pages/admin.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle exception properly
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
