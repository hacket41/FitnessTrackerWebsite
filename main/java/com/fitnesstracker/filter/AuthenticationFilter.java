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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String HOME = "/home";
    private static final String ROOT = "/";
    private static final String ADMIN_PAGE = "/admin";
    private static final String ADMIN_ROLE = "admin";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String role = null;

        // Get role from cookies
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if ("role".equals(cookie.getName())) {
                    role = cookie.getValue();
                    break;
                }
            }
        }

        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

        // Allow CSS/static resources
        if (uri.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        // Allow login, register, and root access without login
        if (!isLoggedIn) {
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.equals(req.getContextPath() + ROOT)) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(req.getContextPath() + LOGIN);
            }
        } else {
            // Block access to /admin if user is not admin
            if (uri.endsWith(ADMIN_PAGE) && !ADMIN_ROLE.equalsIgnoreCase(role)) {
                res.getWriter().write("<h1>Access Denied</h1>");
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Prevent access to login/register once logged in
            if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
                res.sendRedirect(req.getContextPath() + HOME);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
