package com.ejemplo.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsaApplication.class, args);
    }

}
