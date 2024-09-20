package Adobe.com.repositories;

import Adobe.com.domain.Address;

import java.sql.SQLException;

public interface AddressRepository {
    void saveAddress(int companyProfileId, Address address) throws SQLException;
}
