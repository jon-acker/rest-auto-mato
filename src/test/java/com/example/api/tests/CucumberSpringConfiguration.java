package com.example.api.tests;

import io.cucumber.spring.CucumberContextConfiguration;
import org.example.App;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(
        classes = {App.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class CucumberSpringConfiguration { }
