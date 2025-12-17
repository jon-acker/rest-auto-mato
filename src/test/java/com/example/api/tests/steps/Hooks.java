package com.example.api.tests.steps;

import io.cucumber.java8.En;

import static io.restassured.RestAssured.*;

public class Hooks implements En {

    public Hooks() {

        Before(() -> {
            baseURI = "https://api.restful-api.dev";
        });
    }
}
