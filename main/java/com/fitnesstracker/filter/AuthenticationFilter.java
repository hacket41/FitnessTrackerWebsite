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

/**
 * AuthenticationFilter intercepts all requests to:
 * - Allow access to public pages without login.
 * - Restrict access to protected pages to authenticated users.
 * - Enforce role-based access control for admin pages.
 * - Redirect logged-in users away from login/register pages.
 */
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

    /**
     * Main filtering logic:
     * - Extracts the request path.
     * - Checks user login status from session.
     * - Checks role from cookies.
     * - Allows static resources through without checks.
     * - Allows public pages without login.
     * - Restricts admin pages to users with admin role.
     * - Redirects unauthenticated users to login for protected pages.
     * - Redirects logged-in users from login/register to home.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Get URI and path relative to context
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        System.out.println("FILTER: Full URI = " + uri + ", Path = " + path);

        // Retrieve user role from cookies
        String role = null;
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if ("role".equals(cookie.getName())) {
                    role = cookie.getValue();
                    break;
                }
            }
        }

        // Check login status from session attribute "username"
        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

        // Allow static resources to pass through without filtering
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        // Define public accessible pages without login
        boolean isPublicPage = path.equals(ROOT) ||
                               path.startsWith(LOGIN) ||
                               path.startsWith(REGISTER) ||
                               path.startsWith("/meals") ||
                               path.startsWith("/about") ||
                               path.startsWith("/progress") ||
                               path.startsWith("/workout");

        System.out.println("FILTER: isLoggedIn = " + isLoggedIn + ", isPublicPage = " + isPublicPage);

        if (!isLoggedIn) {
            // If not logged in, allow only public pages, else redirect to login
            if (isPublicPage) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(contextPath + LOGIN);
            }
        } else {
            // User is logged in

            // Restrict access to /admin pages for non-admin users
            if (path.startsWith(ADMIN_PAGE) && !ADMIN_ROLE.equalsIgnoreCase(role)) {
                res.getWriter().write("<h1>Access Denied</h1>");
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Redirect logged-in users away from login or register pages to home
            if (path.startsWith(LOGIN) || path.startsWith(REGISTER)) {
                res.sendRedirect(contextPath + HOME);
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
