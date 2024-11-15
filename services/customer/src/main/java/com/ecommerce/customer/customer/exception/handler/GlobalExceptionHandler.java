package com.ecommerce.customer.customer.exception.handler;

import com.ecommerce.customer.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.customer.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Differences between `@ControllerAdvice` & `@RestControllerAdvice` root the kind of responses they handle:
 * - `@ControllerAdvice`: Used to handle exceptions in controllers annotated with `@Controller`. Responses of these kind of controllers might be i.e. HTML files or any other type of response that a regular controller can return.
 * - `@RestControllerAdvice`: It is a special kind of `@ControllerAdvice` that combines `@ControllerAdvice` and `@ResponseBody`. It is used to handle exceptions in controllers annotated with `@RestController`. Responses are always either JSON or XML (suitable for APIs RESTful).
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles `CustomerNotFoundException` exception and return an appropriate response
     *
     * @param ex         exception thrown when a customer is not found.
     * @param webRequest context of the request where the exception occurred
     * @return and entity response that contains the error message and the Http code
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> customerNotFoundExceptionHandler(CustomerNotFoundException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .statusCode(status.value())
                .timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .build();

        return new ResponseEntity<>(errorMessage, status);
    }

    // does not work: response is not returned as an ErrorMessage when a field fails validation
    //todo: replace this method to match the exact same one as in the video (check if it works in that way)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> validationExceptionHandler(MethodArgumentNotValidException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .statusCode(status.value())
                .timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .build();

        return new ResponseEntity<>(errorMessage, status);
    }
}
