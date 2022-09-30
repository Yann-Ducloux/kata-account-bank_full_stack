package com.bank.account.exception;

public class AccountBankHaveNotException extends RuntimeException {

    public AccountBankHaveNotException() {
        super(" l'utilisateur n'a pas de compte ");
    }
}
