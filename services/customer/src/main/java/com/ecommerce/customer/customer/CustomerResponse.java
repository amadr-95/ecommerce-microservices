package com.ecommerce.customer.customer;

public record CustomerResponse(
        String firstName,
        String lastName,
        String email,
        String address
) {
}
