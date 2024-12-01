package com.ecommerce.product.product;

import com.ecommerce.product.product.exception.ProductException;
import com.ecommerce.product.product.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid @NotNull ProductRequest productRequest) {
        return new ResponseEntity<>(productService.createProduct(productRequest), CREATED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> buyProducts(
            @RequestBody @Valid @NotNull List<ProductPurchaseRequest> productPurchaseRequest) throws ProductException {
        return ResponseEntity.ok(productService.buyProducts(productPurchaseRequest));
    }

    @GetMapping("/find/{productId}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable @NotNull Integer productId) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable @NotNull(message = "id cannot be null") Integer productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok().build();
    }


}
