package com.fitnesstracker.util;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.HashSet;
import java.util.Set;
import jakarta.servlet.ServletContext;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // No action needed on creation
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");
        String username = (String) se.getSession().getAttribute("username");

        if (username != null && onlineUsers != null) {
            onlineUsers.remove(username);
            context.setAttribute("onlineUsers", onlineUsers);
        }
    }
}
