package com.bank.account.exception;

public class ClientPasswordFalseException extends RuntimeException {

    public ClientPasswordFalseException() {
        super("Le mot de passe est faux");
    }
}