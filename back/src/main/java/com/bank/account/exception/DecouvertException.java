package com.bank.account.exception;

public class DecouvertException extends RuntimeException {

    public DecouvertException(Long decouvert) {
        super("le decouvert est négatif " + decouvert);
    }
}