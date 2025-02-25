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
                .addresses(customerRequest.addresses())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .addresses(customer.getAddresses())
                .build();
    }
}
