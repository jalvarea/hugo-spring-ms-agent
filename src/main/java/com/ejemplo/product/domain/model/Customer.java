package com.ejemplo.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private String gender;
    private String identification;
    private String address;
    private String phone;
    private String password;
    private Boolean status;
}
