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

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;

	    String uri = req.getRequestURI();
	    String role = null;
	    if (req.getCookies() != null) {
	        for (jakarta.servlet.http.Cookie cookie : req.getCookies()) {
	            if (cookie.getName().equals("role")) {
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

	    // Allow login and register pages without login
	    if (!isLoggedIn) {
	        if (uri.endsWith("/login") || uri.endsWith("/register") || uri.equals(req.getContextPath() + "/")) {
	            chain.doFilter(request, response);
	        } else {
	            res.sendRedirect(req.getContextPath() + "/login");
	        }
	    } else {
	        // Block access to /admin if not admin
	        if (uri.endsWith("/admin") && !"admin".equalsIgnoreCase(role)) {
	            res.getWriter().write("<h1>Access Denied</h1>");
	            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            return;
	        }

	        // Prevent access to login/register once logged in
	        if (uri.endsWith("/login") || uri.endsWith("/register")) {
	            res.sendRedirect(req.getContextPath() + "/home");
	        } else {
	            chain.doFilter(request, response);
	        }
	    }
	}
}
