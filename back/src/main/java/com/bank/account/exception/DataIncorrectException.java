package com.bank.account.exception;

public class DataIncorrectException extends RuntimeException {

    public DataIncorrectException() {
        super("un des caractére est incorrecte");
    }
}
