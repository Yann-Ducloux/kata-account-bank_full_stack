package com.bank.account.exception;

public class ClientMaiExistException extends RuntimeException {

    public ClientMaiExistException(String mail) {
        super(" le mail "+ mail + " existe");
    }
}
