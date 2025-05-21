package com.fitnesstracker.service;

import com.fitnesstracker.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactService {
    private Connection connection;

    /**
     * Constructs a ContactService with a database connection.
     *
     * @param connection the database connection to be used for queries
     */
    public ContactService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves a Contact object to the database.
     *
     * @param contact the Contact object containing data to save
     * @throws SQLException if a database access error occurs
     */
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
