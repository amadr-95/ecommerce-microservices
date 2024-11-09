package com.ecommerce.customer.customer.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends CustomerException {
    private final String message;
}
