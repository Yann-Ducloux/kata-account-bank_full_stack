package com.bank.account.exception;

public class MailNotFillException extends RuntimeException {

    public MailNotFillException() {
        super("le mail n'est pas remplit");
    }
}