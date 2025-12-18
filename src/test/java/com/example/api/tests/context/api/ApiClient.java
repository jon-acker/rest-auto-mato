package com.example.api.tests.context;

import io.restassured.response.Response;

public interface ApiClient {

    Response post(String path, Object body);

    Response get(String path);

    Response delete(String path);
}

