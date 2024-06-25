package com.meli.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:properties")
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Test
    public void testGetEmailUsername() {
        String expectedUsername = "testuser";
        assertEquals(expectedUsername, appConfig.getEmailUsername());
    }

    @Test
    public void testGetEmailPassword() {
        String expectedPassword = "testpassword";
        assertEquals(expectedPassword, appConfig.getEmailPassword());
    }
}
