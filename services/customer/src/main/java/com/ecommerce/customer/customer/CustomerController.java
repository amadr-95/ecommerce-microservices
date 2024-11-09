package com.ecommerce.customer.customer;

import com.ecommerce.customer.customer.exception.CustomerNotFoundException;
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
public class CustomerController {

    //todo: document API with Swagger

    private final CustomerService customerService;
    /**
     * Valid annotation: only validates object properties, not object itself (can be null)
     */
    @PostMapping
    public ResponseEntity<Integer> createCustomer(
            @RequestBody @Valid @NotNull(message = "request cannot be null") CustomerRequest customerRequest) {
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
