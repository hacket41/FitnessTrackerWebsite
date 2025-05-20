package com.fitnesstracker.controller;

import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.service.RegisterService;
import com.fitnesstracker.util.PasswordUtil;
import com.fitnesstracker.util.RedirectionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(
   asyncSupported = true,
   urlPatterns = {"/register"}
)
public class RegisterController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private RegisterService registerService;
   private RedirectionUtil redirectionUtil;

   public RegisterController() {
   }

   public void init() throws ServletException {
      this.registerService = new RegisterService();
      this.redirectionUtil = new RedirectionUtil();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         UserModel userModel = this.extractUserModel(request, response);
         if (userModel == null) {
            return;
         }

         Boolean isAdded = this.registerService.addUser(userModel);
         if (isAdded == null) {
            this.handleError(request, response, "Our server is under maintenance. Please try again later!");
         } else if (isAdded) {
            this.handleSuccess(request, response, "Account created successfully", "/WEB-INF/pages/login.jsp");
         } else {
            this.handleError(request, response, "Could not register your account. Please try again later!");
         }
      } catch (Exception var5) {
         this.handleError(request, response, "An unexpected error occurred. Please try again later!");
         var5.printStackTrace();
      }
   }

   private UserModel extractUserModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
      String fName = request.getParameter("firstName");
      String lName = request.getParameter("lastName");
      String userName = request.getParameter("userName");
      String email = request.getParameter("email");
      String birthdayStr = request.getParameter("birthday");
      String password = request.getParameter("password");
      String retypePassword = request.getParameter("retypePassword");

      // Validate required fields
      if (fName == null || lName == null || userName == null || email == null || birthdayStr == null || password == null) {
         redirectionUtil.setMsgAndRedirect(request, response, "error", "All fields are required!", "/WEB-INF/pages/register.jsp");
         return null;
      }

      if (!password.equals(retypePassword)) {
         redirectionUtil.setMsgAndRedirect(request, response, "error", "Please correct your password & retype-password!", "/WEB-INF/pages/register.jsp");
         return null;
      }

      // Parse birthday with proper error handling
      LocalDate birthDay;
      try {
         birthDay = LocalDate.parse(birthdayStr);
      } catch (Exception e) {
         redirectionUtil.setMsgAndRedirect(request, response, "error", "Invalid date format. Please use YYYY-MM-DD format.", "/WEB-INF/pages/register.jsp");
         return null;
      }

      password = PasswordUtil.encrypt(userName, password);

      // Create and populate UserModel
      UserModel userModel = new UserModel();
      userModel.setF_name(fName);
      userModel.setL_name(lName);
      userModel.setUsername(userName);
      userModel.setEmail(email);
      userModel.setBirthday(birthDay);
      userModel.setPassword(password);

      return userModel;
   }

   private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage) throws ServletException, IOException {
      req.setAttribute("success", message);
      req.getRequestDispatcher(redirectPage).forward(req, resp);
   }

   private void handleError(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
      req.setAttribute("error", message);
      req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
   }
}