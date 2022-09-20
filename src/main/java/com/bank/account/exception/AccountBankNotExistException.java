package com.bank.account.exception;

public class AccountBankNotExistException  extends RuntimeException {

    public AccountBankNotExistException(Long idAccountBank) {
        super(" le compte en banque n'existe pas "+idAccountBank);
    }
}
