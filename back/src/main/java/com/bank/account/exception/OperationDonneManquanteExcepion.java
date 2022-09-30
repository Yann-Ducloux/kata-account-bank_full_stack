package com.bank.account.exception;

public class OperationDonneManquanteExcepion extends RuntimeException {

    public OperationDonneManquanteExcepion() {
        super("donne manquante");
    }
}
