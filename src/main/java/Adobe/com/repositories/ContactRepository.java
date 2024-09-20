package Adobe.com.repositories;

import Adobe.com.domain.Contact;

import java.sql.SQLException;

public interface ContactRepository {
    void saveContact(int companyProfileId, Contact contact) throws SQLException;
}
