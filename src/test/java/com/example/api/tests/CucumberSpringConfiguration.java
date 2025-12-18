package com.example.api.tests;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = TestConfig.class)
//@SpringBootTest(
//        classes = FakeApp.class,
//        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
//)
public class CucumberSpringConfiguration { }
