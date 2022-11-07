package com.bank.account.exception;

public class DonneeNotFillException  extends RuntimeException {
    private static String message = "toutes les données n'ont pas été remplit";
    public DonneeNotFillException() {
        super(message);
    }
}
