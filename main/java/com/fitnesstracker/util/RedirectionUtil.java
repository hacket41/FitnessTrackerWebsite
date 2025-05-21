package com.fitnesstracker.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Utility class to handle page redirection and setting message attributes in requests.
 */
public class RedirectionUtil {

    private static final String baseUrl = "/WEB-INF/pages/";
    public static final String registerUrl = baseUrl + "register.jsp";
    public static final String loginUrl = baseUrl + "login.jsp";
    public static final String homeUrl = baseUrl + "home.jsp";

    /**
     * Sets a message attribute on the request object.
     *
     * @param req     HttpServletRequest object
     * @param msgType the attribute key (e.g., "errorMsg", "successMsg")
     * @param msg     the message string
     */
    public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
        req.setAttribute(msgType, msg);
    }

    /**
     * Forwards the request and response to the specified JSP page.
     *
     * @param req  HttpServletRequest object
     * @param resp HttpServletResponse object
     * @param page the path to the JSP page relative to context
     * @throws ServletException
     * @throws IOException
     */
    public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
            throws ServletException, IOException {
        req.getRequestDispatcher(page).forward(req, resp);
    }

    /**
     * Convenience method to set a message attribute and then forward to a page.
     *
     * @param req     HttpServletRequest object
     * @param resp    HttpServletResponse object
     * @param msgType the attribute key (e.g., "errorMsg", "successMsg")
     * @param msg     the message string
     * @param page    the path to the JSP page
     * @throws ServletException
     * @throws IOException
     */
    public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
            String page) throws ServletException, IOException {
        setMsgAttribute(req, msgType, msg);
        redirectToPage(req, resp, page);
    }
}
