package com.ejemplo.product.domain.port.out;

import com.ejemplo.product.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);

    boolean existsByIdentification(String identification);

    boolean existsByIdentificationAndIdNot(String identification, Long id);

    Customer save(Customer customer);

    void deleteById(Long id);
}
