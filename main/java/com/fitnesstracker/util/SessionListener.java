package com.fitnesstracker.util;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.ServletContext;
import java.util.Set;

/**
 * Listener to manage user sessions and track online users.
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Called when a session is created.
     * No code needed at this time.
     *
     * @param se HttpSessionEvent
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // NO CODE NEEDED YET
    }

    /**
     * Called when a session is destroyed.
     * Removes the username from the set of online users stored in the servlet context.
     *
     * @param se HttpSessionEvent
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();

        @SuppressWarnings("unchecked")
        Set<String> onlineUsers = (Set<String>) context.getAttribute("onlineUsers");

        String username = (String) se.getSession().getAttribute("username");

        if (username != null && onlineUsers != null) {
            onlineUsers.remove(username);
            context.setAttribute("onlineUsers", onlineUsers);
        }
    }
}
