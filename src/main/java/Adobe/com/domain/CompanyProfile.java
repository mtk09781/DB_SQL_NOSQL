package Adobe.com.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CompanyProfile {
    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("preferredLanguage")
    private String preferredLanguage;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("contacts")
    private List<Contact> contacts;

    // Getters and Setters

    @Override
    public String toString() {
        return "CompanyProfile{" +
                "companyName='" + companyName + '\'' +
                ", preferredLanguage='" + preferredLanguage + '\'' +
                ", address=" + address +
                ", contacts=" + contacts +
                '}';
    }
}
