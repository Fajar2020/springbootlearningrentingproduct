package com.springrent.rent_admin_backend.exception;

public class DataNotExistsException extends Exception{
    public DataNotExistsException(String data) {
        super(data+" does not exist");
    }
}
