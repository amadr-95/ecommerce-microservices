package com.ecommerce.customer.customer;

import com.ecommerce.customer.customer.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public Integer createCustomer(CustomerRequest customerRequest) {
        return customerRepository.save(mapper.toCustomer(customerRequest)).getId();
    }

    public void updateCustomer(CustomerRequest customerRequest) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerRequest.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with ID::%s can not be found", customerRequest.id())
                ));
        //update customer values (supposed valid ones validated in Controller layer)
        customer.setFirstName(customerRequest.firstName());
        customer.setLastName(customerRequest.lastName());
        customer.setEmail(customerRequest.email());
        customer.setAddresses(customerRequest.addresses());
        customerRepository.save(customer);
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream()
                .map(mapper::toCustomerResponse)
                .toList();
    }

    public Boolean existById(Integer customerId) {
        return customerRepository.existsById(customerId);
    }

    public CustomerResponse findCustomerById(Integer customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId)
                .map(mapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Customer with ID::%s can not be found", customerId)
                ));
    }

    public void deleteById(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
}
