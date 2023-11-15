package com.pu.elektronendomoupravitel.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String notFoundMessage) {
        super(notFoundMessage);
    }
}
