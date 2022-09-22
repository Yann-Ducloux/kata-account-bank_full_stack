package com.bank.account.exception;

public class ClientMailExistException extends RuntimeException {

    public ClientMailExistException(String mail) {
        super(" le mail "+ mail + " n'existe pas");
    }
}
