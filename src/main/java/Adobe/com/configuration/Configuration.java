package Adobe.com.configuration;

import lombok.Data;

@Data
public class Configuration {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/CUSTOMER";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";
}
