package com.ecommerce.customer.customer;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String firstName,
        String lastName,
        String email,
        String address
) {
}
