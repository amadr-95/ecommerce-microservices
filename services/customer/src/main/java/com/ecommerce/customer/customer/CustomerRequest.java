package com.ecommerce.customer.customer;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        Integer id,
        @NotNull(message = "firstName can not be null")
        String firstName,
        @NotNull(message = "lastName can not be null")
        String lastName,
        @NotNull(message = "email can not be null")
        @Email(message = "email is not a valid email address")
        String email,
        @NotNull(message = "address can not be null")
        String address
) {
}
