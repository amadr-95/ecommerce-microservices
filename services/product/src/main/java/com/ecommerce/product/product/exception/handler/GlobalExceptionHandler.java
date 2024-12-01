package com.ecommerce.product.product.exception.handler;

import com.ecommerce.product.product.exception.ErrorMessage;
import com.ecommerce.product.product.exception.ErrorResponse;
import com.ecommerce.product.product.exception.ProductNotFoundException;
import com.ecommerce.product.product.exception.ProductPurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> customerNotFoundExceptionHandler(ProductNotFoundException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .statusCode(status.value())
                .timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .build();

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<ErrorMessage> customerNotFoundExceptionHandler(ProductPurchaseException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .statusCode(status.value())
                .timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .build();

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validationExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->
                {
                    String field = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(field, errorMessage);
                }
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errors(errors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
