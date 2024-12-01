package com.ecommerce.product.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "name cannot be null")
        String name,
        @NotNull(message = "description cannot be null")
        String description,
        @Positive(message = "quantity must be positive and greater than zero")
        int quantity,
        @Positive(message = "price must be positive and greater than zero")
        BigDecimal price,
        @NotNull(message = "category cannot be null")
        Integer categoryId
) {
}
