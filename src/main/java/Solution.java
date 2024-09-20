
import Adobe.com.controller.Controller;
import Adobe.com.domain.CreateCustomer;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sound.midi.ControllerEventListener;

public class Solution {
    public static void main(String[] args) {
        String jsonInput = "{\"externalReferenceId\":\"123\",\"resellerId\":\"456\",\"companyProfile\":{\"companyName\":\"TestCustomer123\",\"preferredLanguage\":\"en-US\",\"address\":{\"country\":\"US\",\"region\":\"CA\",\"city\":\"SanJose\",\"addressLine1\":\"345ParkAve\",\"addressLine2\":\"\",\"postalCode\":\"95110\"},\"contacts\":[{\"firstName\":\"first_name\",\"lastName\":\"last_name\",\"email\":\"gregor+nophonenumber@adobetest.com\"}]}}";

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateCustomer createCustomer = objectMapper.readValue(jsonInput, CreateCustomer.class);
            Controller.createCustomer(createCustomer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
