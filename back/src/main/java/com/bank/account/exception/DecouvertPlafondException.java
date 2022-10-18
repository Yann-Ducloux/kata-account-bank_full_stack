package com.bank.account.exception;

public class DecouvertPlafondException extends RuntimeException {

    public DecouvertPlafondException() {
        super("le plafond du découvert est dépassé decouvert");
    }
}