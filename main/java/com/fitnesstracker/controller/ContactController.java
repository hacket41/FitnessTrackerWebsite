package com.fitnesstracker.controller;

import com.fitnesstracker.model.Contact;
import com.fitnesstracker.service.ContactService;
import com.fitnesstracker.config.DBConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(asyncSupported = true, urlPatterns = { "/contact" })
public class ContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ContactController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");

        Contact contact = new Contact();
        contact.setUsername(username);
        contact.setEmail(email);

        try {
            Connection conn = DBConfig.getDbConnection();
            ContactService service = new ContactService(conn);
            service.saveContact(contact);
            conn.close();

            request.setAttribute("message", "Thank you! An email will be sent.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Something went wrong. Please try again.");
        }

        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }
}
