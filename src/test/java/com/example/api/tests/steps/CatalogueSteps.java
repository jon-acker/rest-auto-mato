package com.example.api.tests.steps;

import com.example.api.tests.context.CatalogueClient;
import com.example.api.tests.context.Product;
import com.example.api.tests.context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.cucumber.java8.En;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.linesOf;


public class CatalogueSteps implements En {

    private final ScenarioContext context;
    private final CatalogueClient client;

    private boolean isUuid(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public CatalogueSteps(ScenarioContext context, CatalogueClient client) {
        this.context = context;
        this.client = client;


        Given("the catalogue does not contain our products", () -> {
            var response = client.listProducts();

            response.jsonPath()
                    .getList("$", Product.class)
                    .stream()
                    .map(Product::id)
                    .filter(this::isUuid)
                    .forEach(client::deleteProduct);
        });

        Given("the catalogue contains the following products:", (DataTable table) -> {
            table.asList(String.class).forEach(name -> {
                Response response =
                        given()
                                .contentType("application/json")
                                .body("""
                                        { "name": "%s" }
                                        """.formatted(name))
                                .when()
                                .post("/objects");

                context.record(response);
            });
        });

        When("a product named {string} is added to the catalogue", (String name) -> {
            Response response =
                    given()
                            .contentType("application/json")
                            .body("""
                                    { "name": "%s" }
                                    """.formatted(name))
                            .when()
                            .post("/objects");

            var proudctId = response.getBody().jsonPath().get("id");

            context.record(response);

            Response catalogueResponse =
                    given()
                            .when()
                            .get("/objects?id=" + proudctId);

            context.record(catalogueResponse);
        });

        When("the catalogue is asked for a product identified by {string}", (String id) -> {
            Response response =
                    given()
                            .when()
                            .get("/objects/{id}", id);

            context.record(response);
        });

        When("a product is added with malformed data", () -> {
            Response response =
                    given()
                            .contentType("application/json")
                            .body("{\"name\": \"Apple MacBook Pro 16}")
                            .when()
                            .post("/objects");

            context.record(response);
        });

        Then("the catalogue listing for that product should contain the following:", (DataTable table) -> {
            var expected =
                    table.rows(1)
                            .asList(String.class)
                            .stream()
                            .toList();

            var actual =
                    context.lastResponse()
                            .orElseThrow()
                            .jsonPath()
                            .getList("$", Product.class)
                            .stream()
                            .map(Product::name)
                            .toList();

            assertThat(actual).isEqualTo(expected);
        });

        Then("the catalogue indicates the product is not found", () -> {
            int status =
                    context.lastResponse()
                            .orElseThrow()
                            .statusCode();

            assertThat(status).isEqualTo(404);
        });

        Then("the catalogue should reject it", () -> {
            int status =
                    context.lastResponse()
                            .orElseThrow()
                            .statusCode();

            assertThat(status).isEqualTo(400);
        });
    }

}
