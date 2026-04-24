package com.ejemplo.product.infrastructure.mapper;

import com.ejemplo.product.domain.model.Customer;
import com.ejemplo.product.infrastructure.output.persistence.CustomerEntity;
import com.test.services.server.models.CustomerRequest;
import com.test.services.server.models.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer requestToDomain(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .name(request.getName())
                .gender(request.getGender() != null ? request.getGender().getValue() : null)
                .identification(request.getIdentification())
                .address(request.getAddress())
                .phone(request.getPhone())
                .password(request.getPassword())
                .status(request.getStatus())
                .build();
    }

    public CustomerResponse domainToResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .gender(customer.getGender() != null
                        ? CustomerResponse.GenderEnum.fromValue(customer.getGender()) : null)
                .identification(customer.getIdentification())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .status(customer.getStatus())
                .build();
    }

    public Customer entityToDomain(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .gender(entity.getGender())
                .identification(entity.getIdentification())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .password(entity.getPassword())
                .status(entity.getStatus())
                .build();
    }

    public CustomerEntity domainToEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .gender(customer.getGender())
                .identification(customer.getIdentification())
                .address(customer.getAddress())
                .phone(customer.getPhone())
                .password(customer.getPassword())
                .status(customer.getStatus())
                .build();
    }
}
