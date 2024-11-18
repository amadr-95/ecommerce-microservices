package com.ecommerce.customer.customer;


import com.ecommerce.customer.address.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CustomerRequest(
        Integer id,
        @NotNull(message = "firstName cannot be null")
        String firstName,
        @NotNull(message = "lastName cannot be null")
        String lastName,
        @NotNull(message = "email cannot be null")
        @Email(message = "email provided is not a valid email address")
        String email,
        @NotNull(message = "address cannot be null")
        Set<Address> addresses
) {
}
