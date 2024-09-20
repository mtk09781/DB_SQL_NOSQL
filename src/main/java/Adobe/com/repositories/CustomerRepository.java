package Adobe.com.repositories;

import Adobe.com.domain.CreateCustomer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerRepository {
    int saveCustomer(CreateCustomer createCustomer) throws SQLException;
    CreateCustomer findCustomerById(int customerId) throws SQLException;
    CreateCustomer findCustomerByExternalReferenceId(String externalReferenceId) throws SQLException;

    void updateCustomer(int customerId, CreateCustomer createCustomer) throws SQLException;

    void deleteCustomerById(int customerId) throws SQLException;

    List<CreateCustomer> listAllCustomers() throws SQLException;

    List<CreateCustomer> listCustomersByResellerId(String resellerId) throws SQLException;
}
