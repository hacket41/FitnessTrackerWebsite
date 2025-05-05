package com.fitnesstracker.filter;

import java.io.IOException;

import com.fitnesstracker.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/";
    private static final String ADMIN_PAGE = "/admin"; // Admin page URL
    private static final String ADMIN_ROLE = "admin"; // Role for admin

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Cast the request and response to HttpServletRequest and HttpServletResponse
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Get the requested URI
        String uri = req.getRequestURI();

        // Allow CSS and the home root paths to pass through
        if (uri.endsWith(".css") || uri.endsWith(HOME) || uri.endsWith(ROOT)) {
            chain.doFilter(request, response);
            return;
        }

        // Get the session and check if user is logged in
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;
        String role = (String) SessionUtil.getAttribute(req, "role");  // Assuming the role is stored in the session

        if (!isLoggedIn) {
            // If the user is not logged in, allow access to login and register pages
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                chain.doFilter(request, response);
            } else {
                // Redirect to login page
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
        } else {
            // If the user is logged in, check if they are trying to access login or register
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + HOME);
            } else {
                // If the user is an admin, redirect them to the admin page
                if (ADMIN_ROLE.equals(role)) {
                    res.sendRedirect(req.getContextPath() + ADMIN_PAGE);
                } else {
                    // For regular users, proceed with the normal request
                    chain.doFilter(request, response);
                }
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
