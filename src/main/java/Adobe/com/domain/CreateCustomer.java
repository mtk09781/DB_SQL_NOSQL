package Adobe.com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class CreateCustomer {

    @JsonProperty("id")
    private int id;

    @JsonProperty("externalReferenceId")
    private String externalReferenceId;

    @JsonProperty("resellerId")
    private String resellerId;

    @JsonProperty("companyProfile")
    private CompanyProfile companyProfile;

    public CreateCustomer() {
    }

    public CreateCustomer(int id, String externalReferenceId, String resellerId, CompanyProfile companyProfile) {
    }

    // Getters and Setters

    @Override
    public String toString() {
        return "CreateCustomer{" +
                "externalReferenceId='" + externalReferenceId + '\'' +
                ", resellerId='" + resellerId + '\'' +
                ", companyProfile=" + companyProfile +
                '}';
    }
}
