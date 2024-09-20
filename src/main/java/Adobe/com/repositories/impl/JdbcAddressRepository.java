package Adobe.com.repositories.impl;

import Adobe.com.domain.Address;
import Adobe.com.repositories.AddressRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcAddressRepository implements AddressRepository {
    private final Connection connection;

    public JdbcAddressRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveAddress(int companyProfileId, Address address) throws SQLException {
        String sql = "INSERT INTO addresses (company_profile_id, country, region, city, address_line1, address_line2, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, companyProfileId);
            stmt.setString(2, address.getCountry());
            stmt.setString(3, address.getRegion());
            stmt.setString(4, address.getCity());
            stmt.setString(5, address.getAddressLine1());
            stmt.setString(6, address.getAddressLine2());
            stmt.setString(7, address.getPostalCode());
            stmt.executeUpdate();
        }
    }
}
