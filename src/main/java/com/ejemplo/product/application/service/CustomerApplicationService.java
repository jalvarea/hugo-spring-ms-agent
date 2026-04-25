package com.ejemplo.product.application.service;

import com.ejemplo.product.domain.exception.CustomerNotFoundException;
import com.ejemplo.product.domain.exception.DuplicateIdentificationException;
import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.domain.port.in.CustomerUseCase;
import com.ejemplo.product.domain.port.out.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerApplicationService implements CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerApplicationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByIdentification(customer.getIdentification())) {
            throw new DuplicateIdentificationException(customer.getIdentification());
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer customer) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        if (customerRepository.existsByIdentificationAndIdNot(customer.getIdentification(), customerId)) {
            throw new DuplicateIdentificationException(customer.getIdentification());
        }
        customer.setId(customerId);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerRepository.deleteById(customerId);
    }
}
