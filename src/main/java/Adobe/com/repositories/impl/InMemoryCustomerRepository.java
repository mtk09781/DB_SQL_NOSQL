//package Adobe.com.repositories.impl;
//
//import Adobe.com.domain.CompanyProfile;
//import Adobe.com.domain.CreateCustomer;
//import Adobe.com.repositories.CustomerRepository;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class InMemoryCustomerRepository implements CustomerRepository {
//    private final Map<Integer, CreateCustomer> customerMap = new HashMap<>();
//    private final AtomicInteger idGenerator = new AtomicInteger(1);
//
//    @Override
//    public int saveCustomer(CreateCustomer createCustomer) {
//        int customerId = idGenerator.getAndIncrement();
//        CreateCustomer customerWithId = new CreateCustomer(customerId,
//                createCustomer.getExternalReferenceId(),
//                createCustomer.getResellerId(),
//                createCustomer.getCompanyProfile());
//        customerMap.put(customerId, customerWithId);
//        return customerId;
//    }
//
//    @Override
//    public CreateCustomer findCustomerById(int customerId) {
//        return customerMap.get(customerId);
//    }
//
//    @Override
//    public CreateCustomer findCustomerByExternalReferenceId(String externalReferenceId) {
//        return customerMap.values().stream()
//                .filter(customer -> customer.getExternalReferenceId().equals(externalReferenceId))
//                .findFirst()
//                .orElse(null);
//    }
//
//    @Override
//    public void updateCustomer(int customerId, CreateCustomer createCustomer) {
//        CreateCustomer existingCustomer = customerMap.get(customerId);
//        if (existingCustomer != null) {
//            existingCustomer.setExternalReferenceId(createCustomer.getExternalReferenceId());
//            existingCustomer.setResellerId(createCustomer.getResellerId());
//            existingCustomer.setCompanyProfile(createCustomer.getCompanyProfile());
//        }
//    }
//
//    @Override
//    public void deleteCustomerById(int customerId) {
//        customerMap.remove(customerId);
//    }
//
//    @Override
//    public List<CreateCustomer> listAllCustomers() {
//        return new ArrayList<>(customerMap.values());
//    }
//
//    @Override
//    public List<CreateCustomer> listCustomersByResellerId(String resellerId) {
//        List<CreateCustomer> customers = new ArrayList<>();
//        for (CreateCustomer customer : customerMap.values()) {
//            if (customer.getResellerId().equals(resellerId)) {
//                customers.add(customer);
//            }
//        }
//        return customers;
//    }
//}
////    Modifications in CreateCustomer
////        For this example, we assume that the CreateCustomer class has been modified to include an id field and corresponding constructor and getter/setter methods. Here's a simplified version of the class:
////
////        java
////        Copy code
////    class CreateCustomer {
////    private int id; // Added field for in-memory ID
////    private String externalReferenceId;
////    private String resellerId;
////    private CompanyProfile companyProfile;
////
////    // Constructor including id
////    public CreateCustomer(int id, String externalReferenceId, String resellerId, CompanyProfile companyProfile) {
////        this.id = id;
////        this.externalReferenceId = externalReferenceId;
////        this.resellerId = resellerId;
////        this.companyProfile = companyProfile;
////    }
////
////    // Default constructor
////    public CreateCustomer() {}
////
////    public int getId() {
////        return id;
////    }
////
////    public void setId(int id) {
////        this.id = id;
////    }
////
////    public String getExternalReferenceId() {
////        return externalReferenceId;
////    }
////
////    public void setExternalReferenceId(String externalReferenceId) {
////        this.externalReferenceId = externalReferenceId;
////    }
////
////    public String getResellerId() {
////        return resellerId;
////    }
////
////    public void setResellerId(String resellerId) {
////        this.resellerId = resellerId;
////    }
////
////    public CompanyProfile getCompanyProfile() {
////        return companyProfile;
////    }
////
////    public void setCompanyProfile(CompanyProfile companyProfile) {
////        this.companyProfile = companyProfile;
////    }
////
////    @Override
////    public String toString() {
////        return "CreateCustomer{" +
////                "id=" + id +
////                ", externalReferenceId='" + externalReferenceId + '\'' +
////                ", resellerId='" + resellerId + '\'' +
////                ", companyProfile=" + companyProfile +
////                '}';
////    }
////
////}
