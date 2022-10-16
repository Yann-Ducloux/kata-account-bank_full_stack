package com.bank.account.exception;

public class DonneeNotFillException  extends RuntimeException {

    public DonneeNotFillException() {
        super("toutes les données n'ont pas été remplit");
    }
}
