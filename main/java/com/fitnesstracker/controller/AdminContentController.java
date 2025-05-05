package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.service.MealUpload;
import com.fitnesstracker.model.UploadedMeal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
/**
 * Servlet implementation class AdminContentController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admincontent" })
public class AdminContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminContentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try (Connection conn = DBConfig.getDbConnection()) {
	            MealUpload dao = new MealUpload(conn);
	            List<UploadedMeal> meals = dao.getAllMeals();
	            request.setAttribute("uploadedMeals", meals);
	            request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
	        
		 	} 	catch (Exception e) {
	            throw new ServletException("Error fetching uploaded meals", e);
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String mealName = request.getParameter("mealName");
	        String mealType = request.getParameter("mealType");
	        String caloriesStr = request.getParameter("calories");
	        String macros = request.getParameter("macros");

	        if (mealName != null && mealType != null && caloriesStr != null && macros != null) {
	            try (Connection conn = DBConfig.getDbConnection()) {
	                int calories = Integer.parseInt(caloriesStr);

	                UploadedMeal meal = new UploadedMeal();
	                meal.setName(mealName);
	                meal.setType(mealType);
	                meal.setCalories(calories);
	                meal.setMacros(macros);

	                MealUpload dao = new MealUpload(conn);
	                dao.insertMeal(meal);

	                response.sendRedirect(request.getContextPath() + "/admincontent");
	            } catch (Exception e) {
	                throw new ServletException("Error uploading meal", e);
	            }
	        } else {
	            doGet(request, response); // re-display form with error (you can add error messages if needed)
	        }
	}

}


