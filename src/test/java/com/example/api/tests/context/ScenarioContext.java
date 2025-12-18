package com.example.api.tests.context;

import io.cucumber.spring.ScenarioScope;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@ScenarioScope
public class ScenarioContext {

    private Response lastResponse;

    List<String> addedProductIds = new ArrayList<>();

    public Optional<Response> lastResponse() {
        return Optional.ofNullable(lastResponse);
    }

    public List<String> lastProductIds() {
        return addedProductIds;
    }

    public void record(Response response) {
        lastResponse = response;
    }

    public void recordProductId(Response response) {
        addedProductIds.add(response.jsonPath().getString("id"));
    }
}