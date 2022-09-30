package com.bank.account.exception;

public class TypeOperationNotExistException extends RuntimeException {

    public TypeOperationNotExistException() {
        super("ce type d'opération n'existe pas");
    }
}