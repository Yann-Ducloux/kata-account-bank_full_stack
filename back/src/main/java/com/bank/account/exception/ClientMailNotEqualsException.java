package com.bank.account.exception;

public class ClientMailNotEqualsException  extends RuntimeException {

    public ClientMailNotEqualsException(String mail) {
        super(" le mail "+ mail + " n'est pas le bon");
    }
}
