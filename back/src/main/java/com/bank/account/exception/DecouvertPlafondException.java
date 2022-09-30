package com.bank.account.exception;

public class DecouvertPlafondException extends RuntimeException {

    public DecouvertPlafondException(Long decouvert) {
        super("le plafond du découvert est dépassé decouvert:" +decouvert);
    }
}