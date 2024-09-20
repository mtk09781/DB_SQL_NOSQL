package Adobe.com.repositories.impl;

import Adobe.com.domain.CompanyProfile;
import Adobe.com.domain.CreateCustomer;
import Adobe.com.repositories.CustomerRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBCustomerRepository implements CustomerRepository {
    private final MongoCollection<Document> customerCollection;

    public MongoDBCustomerRepository(String connectionString, String databaseName) {
        connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("customersMtk");
        this.customerCollection = database.getCollection("customers");
    }

    @Override
    public int saveCustomer(CreateCustomer createCustomer) throws SQLException {
        Document customerDoc = new Document()
                .append("externalReferenceId", createCustomer.getExternalReferenceId())
                .append("resellerId", createCustomer.getResellerId())
                .append("companyProfile", createCustomer.getCompanyProfile());

        customerCollection.insertOne(customerDoc);
        ObjectId id = customerDoc.getObjectId("_id");
        return Integer.parseInt(id.toHexString()); // Return the ID as a String
    }

    @Override
    public CreateCustomer findCustomerById(int customerId) throws SQLException {
        Document doc = customerCollection.find(Filters.eq("_id", new ObjectId(String.valueOf(customerId)))).first();
        return doc != null ? mapDocumentToCustomer(doc) : null;
    }

    @Override
    public CreateCustomer findCustomerByExternalReferenceId(String externalReferenceId) throws SQLException {
        Document doc = customerCollection.find(Filters.eq("externalReferenceId", externalReferenceId)).first();
        return doc != null ? mapDocumentToCustomer(doc) : null;
    }

    @Override
    public void updateCustomer(int customerId, CreateCustomer createCustomer) throws SQLException {
        customerCollection.updateOne(Filters.eq("_id", new ObjectId(String.valueOf(customerId))),
                Updates.combine(
                        Updates.set("externalReferenceId", createCustomer.getExternalReferenceId()),
                        Updates.set("resellerId", createCustomer.getResellerId()),
                        Updates.set("companyProfile", createCustomer.getCompanyProfile())
                ));
    }

    @Override
    public void deleteCustomerById(int customerId) throws SQLException {
        customerCollection.deleteOne(Filters.eq("_id", new ObjectId(String.valueOf(customerId))));
    }

//    @Override
//    public void deleteCustomerById(String customerId) throws SQLException {
//        customerCollection.deleteOne(Filters.eq("_id", new ObjectId(customerId)));
//    }

    @Override
    public List<CreateCustomer> listAllCustomers() throws SQLException {
        List<CreateCustomer> customers = new ArrayList<>();
        for (Document doc : customerCollection.find()) {
            customers.add(mapDocumentToCustomer(doc));
        }
        return customers;
    }

    @Override
    public List<CreateCustomer> listCustomersByResellerId(String resellerId) throws SQLException {
        List<CreateCustomer> customers = new ArrayList<>();
        for (Document doc : customerCollection.find(Filters.eq("resellerId", resellerId))) {
            customers.add(mapDocumentToCustomer(doc));
        }
        return customers;
    }

    private CreateCustomer mapDocumentToCustomer(Document doc) {
        // Assuming the CreateCustomer and CompanyProfile classes are mapped properly
        return new CreateCustomer(
                Integer.parseInt(doc.getObjectId("_id").toHexString()), // Convert ObjectId to String
                doc.getString("externalReferenceId"),
                doc.getString("resellerId"),
                doc.get("companyProfile", CompanyProfile.class)
        );
    }
}