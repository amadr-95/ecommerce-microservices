package com.ecommerce.product.product.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(
        Map<String, String> errors
) {
}
