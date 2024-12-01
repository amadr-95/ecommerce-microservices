package com.ecommerce.product.product.exception;

import lombok.Builder;

@Builder
public record ErrorMessage(
        String message,
        String description,
        int statusCode,
        String timestamp
) {
}
