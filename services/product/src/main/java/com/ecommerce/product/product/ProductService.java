package com.ecommerce.product.product;

import com.ecommerce.product.product.exception.ProductException;
import com.ecommerce.product.product.exception.ProductNotFoundException;
import com.ecommerce.product.product.exception.ProductPurchaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest productRequest) {
        return productRepository.save(mapper.toProduct(productRequest)).getId();
    }

    @Transactional
    public List<ProductPurchaseResponse> buyProducts(List<ProductPurchaseRequest> productPurchaseRequest) throws ProductException {
        //sort products by ID
        List<ProductPurchaseRequest> productPurchaseRequestSortedById = productPurchaseRequest.stream()
                        .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                        .toList(); //e.g: 5, 46, 3 -> 3, 5, 46

        //Map to store the newAvailableQuantity and update product quantities when the purchase is successful
        Map<Integer, Integer> newQuantities = new HashMap<>();

        //List of product data from DB
        List<Product> products = new ArrayList<>();

        //compare whether products exist in DB and there are enough of them or not
        for (ProductPurchaseRequest purchaseRequest : productPurchaseRequestSortedById) {
            int purchaseRequestId = purchaseRequest.productId();
            int purchaseRequestUnits = purchaseRequest.numberOfUnits();

            //check if each product ID exist in DB
            Product product = productRepository.findById(purchaseRequestId).orElseThrow(() -> new ProductNotFoundException(String.format(
                    "Product with ID::%s can not be found", purchaseRequestId))); //method ends if a product does not exist

            //save product to update the quantity
            products.add(product);

            //check if there are enough units in order to purchase the product
            int availableQuantity = product.getQuantity();
            int newAvailableQuantity = availableQuantity - purchaseRequestUnits;
            if(newAvailableQuantity < 0) throw new ProductPurchaseException(String.format(
                    "Not enough units available. Units available::%s, Units to purchase::%s", availableQuantity, purchaseRequestUnits
            )); //method ends
            //store the new quantity
            newQuantities.put(product.getId(), newAvailableQuantity);
        }

        //Update the quantities and perform the purchase
        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            product.setQuantity(newQuantities.get(product.getId()));
            productRepository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productPurchaseRequest.get(i).numberOfUnits()));
        }
        return purchasedProducts;
    }

    public ProductResponse findProductById(Integer productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException(String.format(
                        "Product with ID::%s can not be found", productId)
                ));
    }

    public List<ProductResponse> findAllProducts() {
        return productRepository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }

}
