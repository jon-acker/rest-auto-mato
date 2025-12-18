package com.example.api.tests.steps;

import io.cucumber.java8.En;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static io.restassured.config.HttpClientConfig.httpClientConfig;
import static io.restassured.specification.ProxySpecification.port;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;

public class Hooks implements En {
    public Hooks() {

        Before(() -> {
            // set a timeout for restassured requests
            RestAssured.config = config()
                    .httpClient(httpClientConfig()
                            .setParam(CONNECTION_TIMEOUT, 5000)
                            .setParam(SO_TIMEOUT, 5000)
                    );

            baseURI = System.getProperty(
                    "api.baseUrl",
                    "https://api.restful-api.dev"
            );

            port(8080);
        });

        After((scenario) -> {
            if (scenario.isFailed()) {
                System.err.println("❌ Fatal connectivity failure – aborting test suite");
                System.exit(1);
            }
        });
    }
}
