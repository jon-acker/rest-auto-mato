package com.example.api.tests.context;

import java.util.Date;
import java.util.Map;

public record Product(String id, String name, Date createdAt, Map<String, Object> data) {}
