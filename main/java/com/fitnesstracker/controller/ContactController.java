package com.fitnesstracker.controller;

import com.fitnesstracker.model.Contact;
import com.fitnesstracker.service.ContactService;
import com.fitnesstracker.config.DBConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

/**
 * Servlet to handle displaying and processing the contact form.
 * Supports GET requests to show the contact page and POST requests to save contact details.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/contact" })
public class ContactController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ContactController() {
        super();
    }

    /**
     * Handles GET requests by forwarding to the contact form JSP page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for submitting contact form data.
     * Reads form parameters, creates a Contact object, saves it using the ContactService,
     * and sets a success or error message to be displayed.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        // Create Contact model and set properties
        Contact contact = new Contact();
        contact.setUsername(username);
        contact.setEmail(email);

        try {
            // Get database connection and instantiate service
            Connection conn = DBConfig.getDbConnection();
            ContactService service = new ContactService(conn);
            
            // Save the contact information to the database
            service.saveContact(contact);
            
            // Close the connection after use
            conn.close();

            // Set success message for the JSP
            request.setAttribute("message", "Thank you! An email will be sent.");
        } catch (Exception e) {
            e.printStackTrace();
            
            // Set error message if something goes wrong
            request.setAttribute("message", "Something went wrong. Please try again.");
        }

        // Forward back to the contact page with message attribute
        request.getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward(request, response);
    }
}
