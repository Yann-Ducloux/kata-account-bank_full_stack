package com.bank.account.controller;

import com.bank.account.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountBankExceptionHandler {

    @ExceptionHandler(value = {MailNotFillException.class})
    public ResponseEntity<Object> notFillHandleError(MailExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("la donnée du mail n'est pas remplit");
    }

    @ExceptionHandler(value = {SoldeException.class})
    public ResponseEntity<Object> incorectDataHandleError(SoldeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("la valeur du solde est incorrecte");
    }

    @ExceptionHandler(value = {DecouvertException.class})
    public ResponseEntity<Object> incorectDataHandleError(DecouvertException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("la valeur du découvert est incorrecte");
    }

    @ExceptionHandler(value = { ClientMailExistException.class})
    public ResponseEntity<Object> mailNotExistHandleError(ClientMailExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le mail n'existe pas");
    }
}
