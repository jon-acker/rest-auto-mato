package com.example.api.tests.steps;

import com.example.api.tests.context.CatalogueClient;
import com.example.api.tests.context.Product;
import com.example.api.tests.context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.cucumber.java8.En;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;


public class CatalogueSteps implements En {

    private boolean isUuid(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public CatalogueSteps(ScenarioContext context, CatalogueClient client) {

        Given("the catalogue does not contain our products", () -> {
            var response = client.listProducts();

            // remove only product ids that are valid UUIDs (ours)
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
            Response response = client.addProduct(name);

            context.record(response);
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
            var productId = context.lastResponse().orElseThrow().getBody().as(Product.class).id();

            Response listing = client.listProductByIds(List.of(productId));

            var expected =
                    table.rows(1)
                            .asList(String.class)
                            .stream()
                            .toList();

            var actual = listing
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

        Given("the product {string} was added to the catalogue", (String name)  -> {
            var response = client.addProduct(name);

            context.record(response);
        });

        When("the product {string} is removed from the catalogue", (String name) -> {
            client.deleteProduct(context.lastProductIds().get(0));
        });

        Then("the catalogue listing should not contain the product {string}", (String id) -> {
            client.listProductByIds(context.lastProductIds())
                    .then()
                    .assertThat()
                    .body("", hasSize(0));
        });

        When("a catalogue request to list both products is made", () -> {
            var response = client.listProductByIds(context.lastProductIds());

            context.record(response);
        });

        Then("the catalogue listing should contain the following:", (DataTable table) -> {
            var expected =
                    table.rows(1)
                            .asList(String.class)
                            .stream()
                            .toList();

            var actual = context.lastResponse().orElseThrow()
                    .jsonPath()
                    .getList("$", Product.class)
                    .stream()
                    .map(Product::name)
                    .toList();

            assertThat(actual).isEqualTo(expected);
        });
    }

}
