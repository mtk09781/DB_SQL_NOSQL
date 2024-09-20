package Adobe.com.repositories.impl;

import Adobe.com.domain.CompanyProfile;
import Adobe.com.repositories.CompanyProfileRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCompanyProfileRepository implements CompanyProfileRepository {
    private final Connection connection;

    public JdbcCompanyProfileRepository(Connection connection) {
        this.connection = connection;
    }



    @Override
    public int saveCompanyProfile(int customerId, CompanyProfile companyProfile) throws SQLException {
        String sql = "INSERT INTO company_profiles (customer_id, company_name, preferred_language) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.setString(2, companyProfile.getCompanyName());
            stmt.setString(3, companyProfile.getPreferredLanguage());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Failed to retrieve company profile ID.");
            }
        }
    }

}
