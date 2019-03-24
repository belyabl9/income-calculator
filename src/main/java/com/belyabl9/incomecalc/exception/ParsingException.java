package com.belyabl9.incomecalc.exception;

public class ParsingException extends RuntimeException {
    public ParsingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
