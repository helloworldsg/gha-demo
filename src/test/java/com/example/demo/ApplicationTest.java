package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTest {

    @Test
    void contextLoads() {
        // Verifies that the Spring application context loads successfully
        throw new RuntimeException("This test is broken on purpose");
    }

    @Test
    void simplePass() {
        assert (true);
    }
}
