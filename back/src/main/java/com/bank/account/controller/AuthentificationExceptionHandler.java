package com.bank.account.controller;

import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.exception.MailExistException;
import com.bank.account.exception.MailIsInvalidEception;
import com.bank.account.exception.MailNotFillException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthentificationExceptionHandler {

    @ExceptionHandler(value = {MailIsInvalidEception.class})
    public ResponseEntity<Object> mailInvalidHandleError(MailIsInvalidEception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le mot de passe est faux");
    }

    @ExceptionHandler(value = {ClientPasswordFalseException.class})
    public ResponseEntity<Object> passwordFalseHandleError(ClientPasswordFalseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le mot de passe est faux");
    }

}
