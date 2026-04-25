package com.ejemplo.product.infrastructure.output.persistence;

import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.domain.port.out.CustomerRepository;
import com.ejemplo.product.infrastructure.mapper.CustomerMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerPersistenceAdapter implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerMapper customerMapper;

    public CustomerPersistenceAdapter(CustomerJpaRepository customerJpaRepository, CustomerMapper customerMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> findAll() {
        return customerJpaRepository.findAll().stream()
                .map(customerMapper::toCustomer)
                .toList();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerJpaRepository.findById(id)
                .map(customerMapper::toCustomer);
    }

    @Override
    public boolean existsByIdentification(String identification) {
        return customerJpaRepository.existsByIdentification(identification);
    }

    @Override
    public boolean existsByIdentificationAndIdNot(String identification, Long id) {
        return customerJpaRepository.existsByIdentificationAndIdNot(identification, id);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = customerMapper.toCustomerEntity(customer);
        CustomerEntity saved = customerJpaRepository.save(entity);
        return customerMapper.toCustomer(saved);
    }

    @Override
    public void deleteById(Long id) {
        customerJpaRepository.deleteById(id);
    }
}
