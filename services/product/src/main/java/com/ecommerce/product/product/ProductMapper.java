package com.ecommerce.product.product;

import com.ecommerce.product.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .quantity(productRequest.quantity())
                .price(productRequest.price())
                .category(Category.builder()
                        .id(productRequest.categoryId())
                        .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getCategory())
                .build();
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, int quantity) {
        return ProductPurchaseResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .productDescription(product.getDescription())
                .numberOfUnits(quantity)
                .categoryName(product.getCategory().getCategory())
                .build();
    }
}
