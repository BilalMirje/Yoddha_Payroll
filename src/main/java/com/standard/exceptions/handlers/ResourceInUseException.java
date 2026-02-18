package com.standard.exceptions.handlers;

public class ResourceInUseException extends RuntimeException{
    public ResourceInUseException(){
        super("Resource in use");
    }

    public ResourceInUseException(String message){
        super(message);
    }
}
