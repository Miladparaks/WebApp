package com.visitors.webapp.model.controller.exceptions;

public class NoVisitFoundException extends Exception{
    public NoVisitFoundException(){
        super("No Visit Found");
    }
}
