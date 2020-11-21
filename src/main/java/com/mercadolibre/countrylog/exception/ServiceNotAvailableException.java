package com.mercadolibre.countrylog.exception;

public class ServiceNotAvailableException extends RuntimeException{
    
    public ServiceNotAvailableException(String message) {
        super(message);
    }
}
