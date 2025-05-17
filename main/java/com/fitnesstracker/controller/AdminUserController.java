package com.fitnesstracker.controller;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.UserFunctions;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(asyncSupported = true, urlPatterns = { "/adminusers" })
public class AdminUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserFunctions userFunctions;

    @Override
    public void init() throws ServletException {
        userFunctions = new UserFunctions();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
            UserFunctions userFunctions = new UserFunctions();
            List<UserModel> userList = userFunctions.getAllUsers();
            int userCount = userFunctions.getUserCount();
            request.setAttribute("userList", userList);
            request.setAttribute("userCount", userCount);
            request.getRequestDispatcher("/WEB-INF/pages/adminuser.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();//Exceptioon test fix
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
        

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
