package com.ecommerce.customer.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private String message;
    private String description;
    private int statusCode;
    private Date timestamp;

}
