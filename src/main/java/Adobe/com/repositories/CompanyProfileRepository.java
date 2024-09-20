package Adobe.com.repositories;

import Adobe.com.domain.CompanyProfile;

import java.sql.SQLException;

public interface CompanyProfileRepository {
    int saveCompanyProfile(int customerId, CompanyProfile companyProfile) throws SQLException;
}
