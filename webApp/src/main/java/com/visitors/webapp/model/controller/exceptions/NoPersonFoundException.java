package com.visitors.webapp.model.controller.exceptions;

public class NoPersonFoundException extends Exception {
    public NoPersonFoundException() {
            super("No Such Person has been found");
    }
}
