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
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        System.out.println("FILTER: Full URI = " + uri + ", Path = " + path);

        String role = null;

        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                if ("role".equals(cookie.getName())) {
                    role = cookie.getValue();
                    break;
                }
            }
        }

        boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

        // Allow static files
        if (path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg")) {
            chain.doFilter(request, response);
            return;
        }

        // Public pages
        boolean isPublicPage = path.equals("/") ||
                               path.startsWith("/login") ||
                               path.startsWith("/register") ||
                               path.startsWith("/meals") ||
                               path.startsWith("/about") ||
                               path.startsWith("/progress") ||
                               path.startsWith("/workout");

        System.out.println("FILTER: isLoggedIn = " + isLoggedIn + ", isPublicPage = " + isPublicPage);

        if (!isLoggedIn) {
            if (isPublicPage) {
                chain.doFilter(request, response);
            } else {
                res.sendRedirect(contextPath + "/login");
            }
        } else {
            if (path.startsWith("/admin") && !"admin".equalsIgnoreCase(role)) {
                res.getWriter().write("<h1>Access Denied</h1>");
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            if (path.startsWith("/login") || path.startsWith("/register")) {
                res.sendRedirect(contextPath + "/home");
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
