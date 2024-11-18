package com.ecommerce.customer.customer;

import com.ecommerce.customer.address.Address;
import lombok.Builder;

import java.util.Set;

@Builder
public record CustomerResponse(
        String firstName,
        String lastName,
        String email,
        Set<Address> addresses
) {
}
