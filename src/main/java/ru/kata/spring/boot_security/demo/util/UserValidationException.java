package ru.kata.spring.boot_security.demo.util;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
