package com.ejemplo.product.infrastructure.output.persistence;

import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.domain.port.out.CustomerRepositoryPort;
import com.ejemplo.product.infrastructure.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerPersistenceAdapter implements CustomerRepositoryPort {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll().stream()
                .map(customerMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::entityToDomain);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = customerMapper.domainToEntity(customer);
        return customerMapper.entityToDomain(customerRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdentification(String identification) {
        return customerRepository.existsByIdentification(identification);
    }

    @Override
    public boolean existsByIdentificationAndIdNot(String identification, Long id) {
        return customerRepository.existsByIdentificationAndIdNot(identification, id);
    }
}
