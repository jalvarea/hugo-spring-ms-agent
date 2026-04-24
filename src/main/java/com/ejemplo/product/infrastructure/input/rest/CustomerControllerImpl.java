package com.ejemplo.product.infrastructure.input.rest;

import com.ejemplo.product.application.service.CustomerApplicationService;
import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.infrastructure.mapper.CustomerMapper;
import com.test.services.server.CustomersApi;
import com.test.services.server.models.CustomerRequest;
import com.test.services.server.models.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CustomerControllerImpl implements CustomersApi {

    private final CustomerApplicationService customerApplicationService;
    private final CustomerMapper customerMapper;

    @Override
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        List<CustomerResponse> responses = customerApplicationService.getCustomers()
                .stream()
                .map(customerMapper::domainToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<CustomerResponse> getCustomerById(Long customerId) {
        Customer customer = customerApplicationService.getCustomerById(customerId);
        return ResponseEntity.ok(customerMapper.domainToResponse(customer));
    }

    @Override
    public ResponseEntity<CustomerResponse> createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.requestToDomain(customerRequest);
        Customer created = customerApplicationService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.domainToResponse(created));
    }

    @Override
    public ResponseEntity<CustomerResponse> updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = customerMapper.requestToDomain(customerRequest);
        Customer updated = customerApplicationService.updateCustomer(customerId, customer);
        return ResponseEntity.ok(customerMapper.domainToResponse(updated));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(Long customerId) {
        customerApplicationService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
