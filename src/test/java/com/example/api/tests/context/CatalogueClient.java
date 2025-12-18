package com.example.api.tests.context;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Component
public class CatalogueClient {
    private final ScenarioContext context;

    public CatalogueClient(ScenarioContext context) {
        this.context = context;
    }

    public Response listProducts() {
        return given()
                .accept(ContentType.JSON)
                .when()
                .get("/objects");
    }

    public Response listProductByIds(List<String> ids) {
        Map<String, List<String>> multiMap = Map.of("id", ids);
        return given()
                .when()
                .queryParams(multiMap)
                .get("/objects");
    }

    public Response deleteProduct(String id) {
        return given()
                .when()
                .delete("/objects/{id}", id);
    }

    public Response addProduct(String name) {
        var response = given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"%s\" }".formatted(name))
                .post("/objects");

        //keep track of the product ids added
        context.recordProductId(response);

        return response;
    }
}
