package com.example.api.tests;

import com.example.api.FakeApiController;
import com.example.api.FakeApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
//@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest(
        classes = FakeApp.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class CucumberSpringConfiguration {
}
