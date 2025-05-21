package com.fitnesstracker.service;

import com.fitnesstracker.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactService {
    private Connection connection;

    public ContactService(Connection connection) {
        this.connection = connection;
    }

    public void saveContact(Contact contact) throws SQLException {
        String sql = "INSERT INTO contact (username, email, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getUsername());
            stmt.setString(2, contact.getEmail());
            if (contact.getUserId() != 0) {
                stmt.setInt(3, contact.getUserId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        }
    }
}
