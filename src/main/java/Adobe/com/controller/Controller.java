package Adobe.com.controller;

import Adobe.com.domain.Address;
import Adobe.com.domain.CompanyProfile;
import Adobe.com.domain.Contact;
import Adobe.com.domain.CreateCustomer;
import Adobe.com.service.CustomerService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestBody;

public class Controller {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CUSTOMER";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    public static String createCustomer( CreateCustomer createCustomer) {
        System.out.println("Creating customer: " + createCustomer);
//        saveCustomerToDB(createCustomer);
//        CustomerService customerService = new CustomerService()
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("customersMtk");
        MongoCollection<org.bson.Document> customerCollection  = database.getCollection("customers");
        saveCustomerToDB(createCustomer, customerCollection);
        return null;
    }

//    public class MongoConnection {
//
//        protected void connectToMongo(String loc){
//
//            String dbName = "readings";
//            String collection = "data";
//
//            MongoClientURI uri = new MongoClientURI("mongodb://user:pass@ds143109.mlab.com:43109/readings");
//            MongoClient client = new MongoClient(uri);
//            MongoDatabase db = client.getDatabase(dbName);
//
//            MongoCollection<Document> readings = db.getCollection(collection);
//
//            Document doc = Document.parse(loc);
//
//            readings.insertOne(doc);
//
//            client.close();
//        }
//    }

//    private static void saveCustomerToDB(CreateCustomer createCustomer) {
//        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            connection.setAutoCommit(false);
//
//            // Insert into customers table
//            String customerSql = "INSERT INTO customers (external_reference_id, reseller_id) VALUES (?, ?)";
//            try (PreparedStatement customerStmt = connection.prepareStatement(customerSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
//                customerStmt.setString(1, createCustomer.getExternalReferenceId());
//                customerStmt.setString(2, createCustomer.getResellerId());
//                customerStmt.executeUpdate();
//
//                // Retrieve generated customer ID
//                try (var rs = customerStmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        int customerId = rs.getInt(1);
//
//                        // Insert into company_profiles table
//                        CompanyProfile companyProfile = createCustomer.getCompanyProfile();
//                        String companyProfileSql = "INSERT INTO company_profiles (customer_id, company_name, preferred_language) VALUES (?, ?, ?)";
//                        try (PreparedStatement companyProfileStmt = connection.prepareStatement(companyProfileSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
//                            companyProfileStmt.setInt(1, customerId);
//                            companyProfileStmt.setString(2, companyProfile.getCompanyName());
//                            companyProfileStmt.setString(3, companyProfile.getPreferredLanguage());
//                            companyProfileStmt.executeUpdate();
//
//                            // Retrieve generated company profile ID
//                            try (var rsCompanyProfile = companyProfileStmt.getGeneratedKeys()) {
//                                if (rsCompanyProfile.next()) {
//                                    int companyProfileId = rsCompanyProfile.getInt(1);
//
//                                    // Insert into addresses table
//                                    Address address = companyProfile.getAddress();
//                                    String addressSql = "INSERT INTO addresses (company_profile_id, country, region, city, address_line1, address_line2, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
//                                    try (PreparedStatement addressStmt = connection.prepareStatement(addressSql)) {
//                                        addressStmt.setInt(1, companyProfileId);
//                                        addressStmt.setString(2, address.getCountry());
//                                        addressStmt.setString(3, address.getRegion());
//                                        addressStmt.setString(4, address.getCity());
//                                        addressStmt.setString(5, address.getAddressLine1());
//                                        addressStmt.setString(6, address.getAddressLine2());
//                                        addressStmt.setString(7, address.getPostalCode());
//                                        addressStmt.executeUpdate();
//                                    }
//
//                                    // Insert into contacts table
//                                    for (Contact contact : companyProfile.getContacts()) {
//                                        String contactSql = "INSERT INTO contacts (company_profile_id, first_name, last_name, email) VALUES (?, ?, ?, ?)";
//                                        try (PreparedStatement contactStmt = connection.prepareStatement(contactSql)) {
//                                            contactStmt.setInt(1, companyProfileId);
//                                            contactStmt.setString(2, contact.getFirstName());
//                                            contactStmt.setString(3, contact.getLastName());
//                                            contactStmt.setString(4, contact.getEmail());
//                                            contactStmt.executeUpdate();
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                connection.commit();
//            } catch (SQLException e) {
//                connection.rollback();
//                throw e;
//            }
//        } catch (SQLException e) {
////            e.printStackTrace();
//        }
//    }




    public static void saveCustomerToDB(CreateCustomer createCustomer, MongoCollection<org.bson.Document> customerCollection) {
        // Create the customer document
        Document customerDoc = new Document("externalReferenceId", createCustomer.getExternalReferenceId())
//                .append("resellerId", createCustomer.getResellerId())
                .append("companyProfile", new Document("companyName", createCustomer.getCompanyProfile().getCompanyName())
                        .append("preferredLanguage", createCustomer.getCompanyProfile().getPreferredLanguage())
                        .append("address", new Document("country", createCustomer.getCompanyProfile().getAddress().getCountry())
                                .append("region", createCustomer.getCompanyProfile().getAddress().getRegion())
                                .append("city", createCustomer.getCompanyProfile().getAddress().getCity())
                                .append("addressLine1", createCustomer.getCompanyProfile().getAddress().getAddressLine1())
                                .append("addressLine2", createCustomer.getCompanyProfile().getAddress().getAddressLine2())
                                .append("postalCode", createCustomer.getCompanyProfile().getAddress().getPostalCode()))
                        .append("contacts", createCustomer.getCompanyProfile().getContacts().stream()
                                .map(contact -> new Document("firstName", contact.getFirstName())
                                        .append("lastName", contact.getLastName())
                                        .append("email", contact.getEmail()))
                                .collect(Collectors.toList()))
                )
                .append("_id", "abc_without_pc");

        // Insert customer document
        customerCollection.insertOne(customerDoc);
    }
}
