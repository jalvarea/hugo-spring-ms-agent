package com.ejemplo.product.application.service;

import com.ejemplo.product.domain.exception.CustomerNotFoundException;
import com.ejemplo.product.domain.exception.DuplicateIdentificationException;
import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.domain.port.in.CustomerUseCase;
import com.ejemplo.product.domain.port.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerApplicationService implements CustomerUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public List<Customer> getCustomers() {
        return customerRepositoryPort.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepositoryPort.existsByIdentification(customer.getIdentification())) {
            throw new DuplicateIdentificationException(customer.getIdentification());
        }
        return customerRepositoryPort.save(customer);
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer customer) {
        customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        if (customerRepositoryPort.existsByIdentificationAndIdNot(customer.getIdentification(), customerId)) {
            throw new DuplicateIdentificationException(customer.getIdentification());
        }
        customer.setId(customerId);
        return customerRepositoryPort.save(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerRepositoryPort.deleteById(customerId);
    }
}
