package org.example.api;

import jakarta.validation.constraints.NotBlank;

public record ProductNameRequest(@NotBlank String name) {
}
