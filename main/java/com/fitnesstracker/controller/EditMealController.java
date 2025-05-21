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

/**
 * Servlet to handle editing of uploaded meals.
 * Processes GET requests to load a meal by ID and forward to the edit meal JSP page.
 * POST requests are not used for form submission here.
 */
@WebServlet("/editmeal")
public class EditMealController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public EditMealController() {
        super();
    }

    /**
     * Handles GET requests to retrieve a meal by ID, set it as a request attribute,
     * and forward to the edit meals JSP page. Redirects to admin content with error messages
     * on invalid or missing IDs or server errors.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // Validate meal ID parameter
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Meal+ID");
            return;
        }

        try {
            int mealId = Integer.parseInt(idParam);
            try (Connection conn = DBConfig.getDbConnection()) {
                MealUploadService mealService = new MealUploadService(conn);
                UploadedMeal meal = mealService.getMealById(mealId);

                // If meal not found, redirect with error message
                if (meal == null) {
                    response.sendRedirect(request.getContextPath() + "/admincontent?error=Meal+not+found");
                    return;
                }

                // Set meal as request attribute for editing
                request.setAttribute("editMeal", meal);
                request.getRequestDispatcher("/WEB-INF/pages/editmeals.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Redirect if meal ID is not a valid number
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Meal+ID+Format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Server+Error");
        }
    }

    /**
     * Handles POST requests by delegating to doGet since form submission is not used here.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // not used for form submission
    }
}
