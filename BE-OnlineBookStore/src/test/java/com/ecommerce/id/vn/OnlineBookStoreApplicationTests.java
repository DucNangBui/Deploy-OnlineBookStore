package com.ecommerce.id.vn;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.datasource.password=${DB_PASSWORD}")
public class OnlineBookStoreApplicationTests {

    @BeforeAll
    static void setUp() {
        // Load .env file using dotenv-java
        Dotenv dotenv = Dotenv.load();

        // Set the system properties based on the .env file
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Test
    void contextLoads() {
        // Logging to check if the DB_PASSWORD is loaded correctly
        System.out.println("Log pass:");
        System.out.println("DB_PASSWORD: " + System.getProperty("DB_PASSWORD"));
        // spring.datasource.password sẽ được lấy từ DB_PASSWORD
    }
}
