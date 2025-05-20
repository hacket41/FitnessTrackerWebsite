package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UploadedWorkout;
import com.fitnesstracker.service.WorkoutUploadService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Servlet implementation class WorkoutController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/workout" })
public class WorkoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try (Connection conn = DBConfig.getDbConnection()) {
	            WorkoutUploadService dao = new WorkoutUploadService(conn);
	            List<UploadedWorkout> workouts = dao.getAllWorkouts();
	            request.setAttribute("workoutList", workouts);
	            request.getRequestDispatcher("/WEB-INF/pages/workoutmain.jsp").forward(request, response);
	        } catch (Exception e) {
	            throw new ServletException("Error fetching workouts", e);
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
