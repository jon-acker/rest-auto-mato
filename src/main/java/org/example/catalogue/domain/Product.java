package org.example.catalogue.domain;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Map;

public record Product(String id, @NotBlank String name, Date createdAt, Date updatedAt, Map<String, Object> data) {
}
