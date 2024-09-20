package Adobe.com.service;

import Adobe.com.configuration.Configuration;
import Adobe.com.configuration.Configuration.*;
import Adobe.com.domain.Address;
import Adobe.com.domain.CompanyProfile;
import Adobe.com.domain.Contact;
import Adobe.com.domain.CreateCustomer;
import Adobe.com.repositories.AddressRepository;
import Adobe.com.repositories.CompanyProfileRepository;
import Adobe.com.repositories.ContactRepository;
import Adobe.com.repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyProfileRepository companyProfileRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    public CustomerService(CustomerRepository customerRepository, CompanyProfileRepository companyProfileRepository,
                           AddressRepository addressRepository, ContactRepository contactRepository) {
        this.customerRepository = customerRepository;
        this.companyProfileRepository = companyProfileRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    public void saveCustomer(CreateCustomer createCustomer) {
        try (Connection connection = DriverManager.getConnection(Configuration.DB_URL, Configuration.DB_USER, Configuration.DB_PASSWORD)) {
            connection.setAutoCommit(false);

            try {
                int customerId = customerRepository.saveCustomer(createCustomer);
                CompanyProfile companyProfile = createCustomer.getCompanyProfile();
                int companyProfileId = companyProfileRepository.saveCompanyProfile(customerId, companyProfile);
                Address address = companyProfile.getAddress();
                addressRepository.saveAddress(companyProfileId, address);

                for (Contact contact : companyProfile.getContacts()) {
                    contactRepository.saveContact(companyProfileId, contact);
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception properly
        }
    }
}
