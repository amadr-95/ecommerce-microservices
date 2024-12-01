package com.ecommerce.product.product;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductPurchaseResponse(
        Integer productId,
        String productName,
        Integer numberOfUnits,
        BigDecimal productPrice,
        String productDescription,
        String categoryName
) {
}
