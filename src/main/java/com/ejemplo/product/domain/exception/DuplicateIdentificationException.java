package com.ejemplo.product.domain.exception;

public class DuplicateIdentificationException extends RuntimeException {

    public DuplicateIdentificationException(String identification) {
        super("Customer already exists with identification: " + identification);
    }
}
