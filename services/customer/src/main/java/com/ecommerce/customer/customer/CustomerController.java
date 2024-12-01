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

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Microservice", description = "REST operations related to customers")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "Create a new customer",
            description = "Given a valid CustomerRequest, it is mapped to a Customer entity and stored in the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<Integer> createCustomer(
            @RequestBody @Valid @NotNull(message = "request cannot be null") CustomerRequest customerRequest) { // Valid annotation: only validates object properties, not object itself (can be null)
        return new ResponseEntity<>(customerService.createCustomer(customerRequest), CREATED);
    }

    @Operation(
            summary = "Update a customer",
            description = "Given a valid CustomerRequest, it updates an existing Customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping
    public ResponseEntity<?> updateCustomer(
            @RequestBody @Valid @NotNull(message = "request cannot be null") CustomerRequest customerRequest) throws CustomerNotFoundException {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Retrieve all customers",
            description = "Retrieves a list of all customers in the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
            }
    )
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @Operation(
            summary = "Check if a customer exists",
            description = "Checks if a customer exists in the database by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer exists"),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(path = "/exists/{customerId}")
    public ResponseEntity<Boolean> existsById(@PathVariable @NotNull Integer customerId) {
        return ResponseEntity.ok(customerService.existById(customerId));
    }

    @Operation(
            summary = "Find a customer by ID",
            description = "Retrieves a customer from the database by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer found", content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping(path = "/find/{customerId}")
    public ResponseEntity<CustomerResponse> findCustomerById(@PathVariable @NotNull Integer customerId) throws CustomerNotFoundException {
        return ResponseEntity.ok(customerService.findCustomerById(customerId));
    }

    @Operation(
            summary = "Delete a customer by ID",
            description = "Deletes a customer from the database by their ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer deleted"),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping(path = "/delete/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable @NotNull Integer customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.ok().build();
    }
    //todo: implement more endpoints i.e search for name, etc
}
