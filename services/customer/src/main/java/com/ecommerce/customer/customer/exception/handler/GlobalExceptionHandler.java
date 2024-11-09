package com.ecommerce.customer.customer.exception.handler;

import com.ecommerce.customer.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.customer.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Differences between `@ControllerAdvice` & `@RestControllerAdvice` root the kind of responses they handle:
 * - `@ControllerAdvice`: Se utiliza para manejar excepciones en controladores anotados con `@Controller`. Las respuestas pueden ser vistas (por ejemplo, páginas HTML) o cualquier otro tipo de respuesta que un controlador regular pueda devolver.
 * - `@RestControllerAdvice`: Es una especialización de `@ControllerAdvice` que combina `@ControllerAdvice` y `@ResponseBody`. Se utiliza para manejar excepciones en controladores anotados con `@RestController`. Las respuestas son siempre JSON o XML, es decir, respuestas adecuadas para APIs RESTful.
 * En resumen, si estás desarrollando una API RESTful, es más apropiado usar `@RestControllerAdvice` para asegurarte de que las respuestas sean en formato JSON o XML.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja la excepción `CustomerNotFoundException` y devuelve una respuesta adecuada.
     *
     * @param ex         la excepción lanzada cuando no se encuentra un cliente
     * @param webRequest el contexto de la solicitud web en la que ocurrió la excepción
     * @return una entidad de respuesta que contiene el mensaje de error y el código de estado HTTP
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> customerNotFoundExceptionHandler(CustomerNotFoundException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .statusCode(status.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(errorMessage, status);
    }

    /**
     * Maneja la excepción `MethodArgumentNotValidException` que es lanzada cuando alguna validacion no se cumple
     *
     * @param ex         la excepción lanzada cuando un argumento de método no es válido
     * @param webRequest el contexto de la solicitud web en la que ocurrió la excepción
     * @return una entidad de respuesta que contiene el mensaje de error y el código de estado HTTP
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> validationExceptionHandler(MethodArgumentNotValidException ex, WebRequest webRequest) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .statusCode(status.value())
                .timestamp(new Date())
                .build();

        return new ResponseEntity<>(errorMessage, status);
    }
}
