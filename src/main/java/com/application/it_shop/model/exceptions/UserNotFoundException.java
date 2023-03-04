package com.application.it_shop.model.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super(String.format("User with this id: %ld was not found!",id));
    }
}
