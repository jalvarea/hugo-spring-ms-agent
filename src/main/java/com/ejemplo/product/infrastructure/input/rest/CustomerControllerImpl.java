package com.ejemplo.product.infrastructure.input.rest;

import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.domain.port.in.CustomerUseCase;
import com.ejemplo.product.infrastructure.mapper.CustomerMapper;
import com.test.services.server.CustomersApi;
import com.test.services.server.models.CustomerRequest;
import com.test.services.server.models.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerControllerImpl implements CustomersApi {

    private final CustomerUseCase customerUseCase;
    private final CustomerMapper customerMapper;

    public CustomerControllerImpl(CustomerUseCase customerUseCase, CustomerMapper customerMapper) {
        this.customerUseCase = customerUseCase;
        this.customerMapper = customerMapper;
    }

    @Override
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        List<CustomerResponse> customers = customerUseCase.getCustomers().stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
        return ResponseEntity.ok(customers);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerById(Long customerId) {
        Customer customer = customerUseCase.getCustomerById(customerId);
        return ResponseEntity.ok(customerMapper.toCustomerResponse(customer));
    }

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.toCustomer(customerRequest);
        Customer created = customerUseCase.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toCustomerResponse(created));
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = customerMapper.toCustomer(customerRequest);
        Customer updated = customerUseCase.updateCustomer(customerId, customer);
        return ResponseEntity.ok(customerMapper.toCustomerResponse(updated));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(Long customerId) {
        customerUseCase.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
