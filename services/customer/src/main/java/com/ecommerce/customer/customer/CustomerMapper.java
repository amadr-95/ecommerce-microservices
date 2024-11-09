package com.ecommerce.customer.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    /**
     * Builder vs Getters & Setters: immutable object vs non immutable object
    **/
    public Customer toCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(customerRequest.address())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
