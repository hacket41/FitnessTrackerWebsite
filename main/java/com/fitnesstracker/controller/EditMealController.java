package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UploadedMeal;
import com.fitnesstracker.service.MealUploadService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/editmeal")
public class EditMealController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditMealController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Meal+ID");
            return;
        }

        try {
            int mealId = Integer.parseInt(idParam);
            try (Connection conn = DBConfig.getDbConnection()) {
                MealUploadService mealService = new MealUploadService(conn);
                UploadedMeal meal = mealService.getMealById(mealId);
                
                if (meal == null) {
                    response.sendRedirect(request.getContextPath() + "/admincontent?error=Meal+not+found");
                    return;
                }

                request.setAttribute("editMeal", meal);
                request.getRequestDispatcher("/WEB-INF/pages/editmeals.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Meal+ID+Format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Server+Error");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // not used for form submission
    }
}
