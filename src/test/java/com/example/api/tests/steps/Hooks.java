package com.example.api.tests.steps;

import io.cucumber.java8.En;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import static io.restassured.RestAssured.*;

public class Hooks implements En {

    public Hooks() {

        Before(() -> {
            baseURI = System.getProperty(
                    "api.baseUrl",
                    "https://api.restful-api.dev"
            );
        });
    }
}
