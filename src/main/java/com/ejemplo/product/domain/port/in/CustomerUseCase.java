package com.ejemplo.product.domain.port.in;

import com.ejemplo.product.domain.model.Customer;

import java.util.List;

public interface CustomerUseCase {

    List<Customer> getCustomers();

    Customer getCustomerById(Long customerId);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long customerId, Customer customer);

    void deleteCustomer(Long customerId);
}
