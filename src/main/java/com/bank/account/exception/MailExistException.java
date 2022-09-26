package com.bank.account.exception;
public class MailExistException  extends RuntimeException {

    public MailExistException(String mail) {
        super("le mail existe déjà:" +mail);
    }
}