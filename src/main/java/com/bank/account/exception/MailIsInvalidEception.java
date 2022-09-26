package com.bank.account.exception;
public class MailIsInvalidEception  extends RuntimeException {

    public MailIsInvalidEception(String  mail) {

        super("Mail invalide:" +mail);
    }
}