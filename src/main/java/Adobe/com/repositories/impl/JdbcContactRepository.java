package Adobe.com.repositories.impl;

import Adobe.com.domain.Contact;
import Adobe.com.repositories.ContactRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContactRepository implements ContactRepository {
    private final Connection connection;

    public JdbcContactRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveContact(int companyProfileId, Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (company_profile_id, first_name, last_name, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, companyProfileId);
            stmt.setString(2, contact.getFirstName());
            stmt.setString(3, contact.getLastName());
            stmt.setString(4, contact.getEmail());
            stmt.executeUpdate();
        }
    }
}
