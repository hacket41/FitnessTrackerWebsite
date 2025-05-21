package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.service.MealUploadService;
import com.fitnesstracker.model.UploadedMeal;
import com.fitnesstracker.service.WorkoutUploadService;
import com.fitnesstracker.model.UploadedWorkout;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet implementation class AdminContentController
 * Handles admin functionality for managing uploaded meals and workouts.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admincontent" })
public class AdminContentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AdminContentController.class.getName());

    /**
     * Default constructor
     */
    public AdminContentController() {
        super();
    }

    /**
     * Handles GET requests for viewing and deleting meals or workouts.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DBConfig.getDbConnection()) {
            MealUploadService mealService = new MealUploadService(conn);
            WorkoutUploadService workoutService = new WorkoutUploadService(conn);

            // Handle meal deletion
            if ("deleteMeal".equals(action)) {
                String mealIdStr = request.getParameter("mealId");
                if (mealIdStr == null || mealIdStr.trim().isEmpty()) {
                    request.setAttribute("error", "Meal ID is missing.");
                    request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
                    return;
                }
                int mealId = Integer.parseInt(mealIdStr);
                mealService.deleteMeal(mealId);
                response.sendRedirect(request.getContextPath() + "/admincontent");
                return;
            }

            // Handle workout deletion
            if ("deleteWorkout".equals(action)) {
                String workoutIdStr = request.getParameter("workoutId");
                if (workoutIdStr == null || workoutIdStr.trim().isEmpty()) {
                    request.setAttribute("error", "Workout ID is missing.");
                    request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
                    return;
                }
                int workoutId = Integer.parseInt(workoutIdStr);
                workoutService.deleteWorkout(workoutId);
                response.sendRedirect(request.getContextPath() + "/admincontent");
                return;
            }

            // Handle success message forwarding
            HttpSession session = request.getSession(false);
            if (session != null) {
                String successMessage = (String) session.getAttribute("successMessage");
                if (successMessage != null) {
                    request.setAttribute("successMessage", successMessage);
                    session.removeAttribute("successMessage");
                }
            }

            // Load meals and workouts for admin view
            List<UploadedMeal> meals = mealService.getSuggestedMeals();
            List<UploadedWorkout> workouts = workoutService.getAllWorkouts();

            request.setAttribute("suggestedMeals", meals);
            request.setAttribute("uploadedWorkouts", workouts);

            request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.severe("Error in GET: " + e.getMessage());
            throw new ServletException("Error loading admin content", e);
        }
    }

    /**
     * Handles POST requests for uploading and editing meals or workouts.
     *
     * @param request  the HttpServletRequest object that contains the request
     * @param response the HttpServletResponse object that contains the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an input or output error is detected
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DBConfig.getDbConnection()) {
            MealUploadService mealService = new MealUploadService(conn);
            WorkoutUploadService workoutService = new WorkoutUploadService(conn);

            HttpSession session = request.getSession();

            switch (action) {
                // Handle meal upload
                case "uploadMeal":
                    try {
                        String mealName = request.getParameter("mealName");
                        String mealType = request.getParameter("mealType");
                        String caloriesStr = request.getParameter("calories");
                        String macros = request.getParameter("macros");

                        if (mealName == null || mealName.trim().isEmpty() ||
                            mealType == null || mealType.trim().isEmpty() ||
                            caloriesStr == null || caloriesStr.trim().isEmpty() ||
                            macros == null || macros.trim().isEmpty()) {
                            request.setAttribute("error", "All meal fields are required.");
                            request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
                            return;
                        }

                        int calories = Integer.parseInt(caloriesStr);

                        UploadedMeal meal = new UploadedMeal();
                        meal.setName(mealName);
                        meal.setType(mealType);
                        meal.setCalories(calories);
                        meal.setMacros(macros);

                        mealService.insertMeal(meal);
                        session.setAttribute("successMessage", "Meal uploaded successfully.");
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Invalid calorie input.");
                        request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
                        return;
                    }
                    break;

                // Handle workout upload
                case "uploadWorkout":
                    String workoutName = request.getParameter("workoutName");
                    String workoutType = request.getParameter("workoutType");
                    String workoutDuration = request.getParameter("workoutDuration");

                    if (workoutName == null || workoutName.trim().isEmpty() ||
                        workoutType == null || workoutType.trim().isEmpty() ||
                        workoutDuration == null || workoutDuration.trim().isEmpty()) {
                        request.setAttribute("error", "All workout fields are required.");
                        request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
                        return;
                    }

                    UploadedWorkout workout = new UploadedWorkout();
                    workout.setName(workoutName);
                    workout.setType(workoutType);
                    workout.setDuration(workoutDuration);

                    workoutService.insertWorkout(workout);
                    session.setAttribute("successMessage", "Workout uploaded successfully.");
                    break;

                // Handle meal editing
                case "editMeal":
                    try {
                        String mealIdStr = request.getParameter("mealId");
                        String caloriesStr = request.getParameter("calories");
                        String mealName = request.getParameter("mealName");
                        String mealType = request.getParameter("mealType");
                        String macros = request.getParameter("macros");

                        if (mealIdStr == null || mealIdStr.trim().isEmpty() ||
                            caloriesStr == null || caloriesStr.trim().isEmpty() ||
                            mealName == null || mealName.trim().isEmpty() ||
                            mealType == null || mealType.trim().isEmpty() ||
                            macros == null || macros.trim().isEmpty()) {
                            request.setAttribute("error", "All meal fields are required.");
                            request.getRequestDispatcher("/WEB-INF/pages/editmeals.jsp").forward(request, response);
                            return;
                        }

                        UploadedMeal updatedMeal = new UploadedMeal();
                        updatedMeal.setId(Integer.parseInt(mealIdStr));
                        updatedMeal.setName(mealName);
                        updatedMeal.setType(mealType);
                        updatedMeal.setCalories(Integer.parseInt(caloriesStr));
                        updatedMeal.setMacros(macros);

                        mealService.updateMeal(updatedMeal);
                        session.setAttribute("successMessage", "Meal updated successfully.");
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Invalid input for editing meal.");
                        request.getRequestDispatcher("/WEB-INF/pages/editmeals.jsp").forward(request, response);
                        return;
                    }
                    break;

                // Handle workout editing
                case "editWorkout":
                    String workoutIdStr = request.getParameter("workoutId");
                    String updatedWorkoutName = request.getParameter("workoutName");
                    String updatedWorkoutType = request.getParameter("workoutType");
                    String updatedWorkoutDuration = request.getParameter("workoutDuration");

                    if (workoutIdStr == null || workoutIdStr.trim().isEmpty() ||
                        updatedWorkoutName == null || updatedWorkoutName.trim().isEmpty() ||
                        updatedWorkoutType == null || updatedWorkoutType.trim().isEmpty() ||
                        updatedWorkoutDuration == null || updatedWorkoutDuration.trim().isEmpty()) {
                        request.setAttribute("error", "All workout fields are required.");
                        request.getRequestDispatcher("/WEB-INF/pages/editworkout.jsp").forward(request, response);
                        return;
                    }

                    UploadedWorkout updatedWorkout = new UploadedWorkout();
                    updatedWorkout.setId(Integer.parseInt(workoutIdStr));
                    updatedWorkout.setName(updatedWorkoutName);
                    updatedWorkout.setType(updatedWorkoutType);
                    updatedWorkout.setDuration(updatedWorkoutDuration);

                    workoutService.updateWorkout(updatedWorkout);
                    session.setAttribute("successMessage", "Workout updated successfully.");
                    break;

                // Handle unknown action case
                default:
                    request.setAttribute("error", "Unknown action: " + action);
                    request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
                    return;
            }

            response.sendRedirect(request.getContextPath() + "/admincontent");

        } catch (Exception e) {
            LOGGER.severe("Error in POST: " + e.getMessage());
            throw new ServletException("Error processing admin content", e);
        }
    }
}
