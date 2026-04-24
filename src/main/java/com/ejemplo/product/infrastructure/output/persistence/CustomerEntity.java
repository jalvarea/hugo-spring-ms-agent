package com.ejemplo.product.infrastructure.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false, unique = true, length = 50)
    private String identification;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 50)
    private String phone;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private Boolean status;
}
