package com.ecommerce.product.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String name,
        String description,
        int quantity,
        BigDecimal price,
        Integer categoryId,
        String categoryName
) {
}
