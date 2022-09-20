package com.bank.account.exception;

public class OperationSommeNulException extends RuntimeException {

    public OperationSommeNulException(Long somme) {
        super("la somme est négatif ou égal à 0 somme:" +somme);
    }
}