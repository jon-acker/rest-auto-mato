package com.example.api.tests.context;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ScenarioContext {

    private Response lastResponse;

    public Optional<Response> lastResponse() {
        return Optional.ofNullable(lastResponse);
    }

    public void record(Response response) {
        this.lastResponse = response;
    }
}