package Adobe.com.repositories.impl;

import Adobe.com.domain.CreateCustomer;
import Adobe.com.repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcCustomerRepository implements CustomerRepository {

    private final Connection connection;

    public JdbcCustomerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int saveCustomer(CreateCustomer createCustomer) throws SQLException {
        String sql = "INSERT INTO customers (external_reference_id, reseller_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, createCustomer.getExternalReferenceId());
            stmt.setString(2, createCustomer.getResellerId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Failed to retrieve customer ID.");
            }
        }
    }

    @Override
    public CreateCustomer findCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Map result set to CreateCustomer object
                    CreateCustomer customer = new CreateCustomer();
                    customer.setExternalReferenceId(rs.getString("external_reference_id"));
                    customer.setResellerId(rs.getString("reseller_id"));
                    return customer;
                }
                return null; // Customer not found
            }
        }
    }

    @Override
    public CreateCustomer findCustomerByExternalReferenceId(String externalReferenceId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE external_reference_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, externalReferenceId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Map result set to CreateCustomer object
                    CreateCustomer customer = new CreateCustomer();
                    customer.setExternalReferenceId(rs.getString("external_reference_id"));
                    customer.setResellerId(rs.getString("reseller_id"));
                    return customer;
                }
                return null; // Customer not found
            }
        }
    }

    @Override
    public void updateCustomer(int customerId, CreateCustomer createCustomer) throws SQLException {
        String sql = "UPDATE customers SET external_reference_id = ?, reseller_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, createCustomer.getExternalReferenceId());
            stmt.setString(2, createCustomer.getResellerId());
            stmt.setInt(3, customerId);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteCustomerById(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<CreateCustomer> listAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customers";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<CreateCustomer> customers = new ArrayList<>();
            while (rs.next()) {
                CreateCustomer customer = new CreateCustomer();
                customer.setExternalReferenceId(rs.getString("external_reference_id"));
                customer.setResellerId(rs.getString("reseller_id"));
                customers.add(customer);
            }
            return customers;
        }
    }

    @Override
    public List<CreateCustomer> listCustomersByResellerId(String resellerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE reseller_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, resellerId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<CreateCustomer> customers = new ArrayList<>();
                while (rs.next()) {
                    CreateCustomer customer = new CreateCustomer();
                    customer.setExternalReferenceId(rs.getString("external_reference_id"));
                    customer.setResellerId(rs.getString("reseller_id"));
                    customers.add(customer);
                }
                return customers;
            }
        }
    }
}
