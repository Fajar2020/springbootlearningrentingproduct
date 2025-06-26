package com.springrent.rent_admin_backend.exception;

public class DataAlreadyExistsException extends Exception{
    public DataAlreadyExistsException(String data) {
        super(data + " already exists");
    }
}
