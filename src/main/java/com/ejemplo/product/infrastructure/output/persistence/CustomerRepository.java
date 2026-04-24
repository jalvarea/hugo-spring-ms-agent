package com.ejemplo.product.infrastructure.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsByIdentification(String identification);

    boolean existsByIdentificationAndIdNot(String identification, Long id);
}
