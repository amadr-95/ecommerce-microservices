package com.ecommerce.product.product;

public record ProductPurchaseRequest(
        Integer productId,
        Integer numberOfUnits
) {
}
