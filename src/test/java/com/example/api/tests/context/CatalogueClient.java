package com.example.api.tests.context;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class CatalogueClient {

    public Response listProducts() {
        return given()
                .when()
                .get("/objects");
    }

    public Response deleteProduct(String id) {
        return given()
                .when()
                .delete("/objects/{id}", id);
    }

    public Response addProduct(String name) {
        return given()
                .contentType("application/json")
                .body("{ \"name\": \"%s\" }".formatted(name))
                .post("/objects");
    }
}
