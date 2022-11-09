package com.bank.account.exception;

public class AccountBankErrorException extends RuntimeException {

    public AccountBankErrorException() {
        super("erreur du compte en banque");
    }
}