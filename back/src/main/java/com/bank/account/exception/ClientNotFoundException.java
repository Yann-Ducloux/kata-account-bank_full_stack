package com.bank.account.exception;

public class ClientNotFoundException extends RuntimeException {

        public ClientNotFoundException(Long id) {
                super("on ne trouve pas le client " + id);
        }
}
