package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UploadedWorkout;
import com.fitnesstracker.service.WorkoutUploadService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/editworkout")
public class EditWorkoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditWorkoutController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Workout+ID");
            return;
        }

        try {
            int workoutId = Integer.parseInt(idParam);
            try (Connection conn = DBConfig.getDbConnection()) {
                WorkoutUploadService workoutService = new WorkoutUploadService(conn);
                UploadedWorkout workout = workoutService.getWorkoutById(workoutId);
                
                if (workout == null) {
                    response.sendRedirect(request.getContextPath() + "/admincontent?error=Workout+not+found");
                    return;
                }

                request.setAttribute("editWorkout", workout);
                request.getRequestDispatcher("/WEB-INF/pages/editworkout.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Workout+ID+Format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Server+Error");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }
}
