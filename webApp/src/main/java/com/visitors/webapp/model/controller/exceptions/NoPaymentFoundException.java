package com.visitors.webapp.model.controller.exceptions;

public class NoPaymentFoundException extends Exception{
    public NoPaymentFoundException(){
        super("No Payment Found");
    }
}
