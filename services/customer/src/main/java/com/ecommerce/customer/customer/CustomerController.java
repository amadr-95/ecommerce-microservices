package com.ecommerce.customer.customer;

import com.ecommerce.customer.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.customer.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Microservice", description = "REST operations related to customers")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Creates a new customer",
            description = "Given a valid CustomerRequest is mapped to a Customer entity and stored in the database",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = CustomerRequest.class), mediaType = "application/json")
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            content = {
                                    @Content(schema = @Schema(implementation = ErrorMessage.class), mediaType = "application/json")
                            }),
                    @ApiResponse(
                            responseCode = "500",
                            content = {
                                    @Content(schema = @Schema())
                            })
            }
    )
    @PostMapping
    public ResponseEntity<Integer> createCustomer(
            @RequestBody @Valid @NotNull(message = "request cannot be null") CustomerRequest customerRequest) { // Valid annotation: only validates object properties, not object itself (can be null)
        return new ResponseEntity<>(customerService.createCustomer(customerRequest), CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(
            @RequestBody @Valid @NotNull(message = "request cannot be null") CustomerRequest customerRequest) throws CustomerNotFoundException {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), OK);
    }

    @GetMapping(path = "/exists/{customerId}")
    public ResponseEntity<Boolean> existsById(@PathVariable @NotNull Integer customerId) {
        return new ResponseEntity<>(customerService.existById(customerId), OK);
    }

    @GetMapping(path = "/find/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable @NotNull Integer customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.findCustomerById(customerId), OK);
    }

    @DeleteMapping(path = "/delete/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable @NotNull Integer customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.ok().build();
    }
}
