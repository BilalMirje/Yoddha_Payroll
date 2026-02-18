package com.standard.exceptions.handlers;

public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String message){
        super(message);
    }

    public DuplicateRecordException(){
        super("Duplicate Record Found");
    }
}
