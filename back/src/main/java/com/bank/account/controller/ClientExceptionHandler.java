package com.bank.account.controller;

import com.bank.account.exception.DataIncorrectException;
import com.bank.account.exception.DonneeNotFillException;
import com.bank.account.exception.MailExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value = { MailExistException.class})
    public ResponseEntity<Object> mailExistHandleError(MailExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le mail existe dèjà");
    }

    @ExceptionHandler(value = { DataIncorrectException.class})
    public ResponseEntity<Object> mailExistHandleError(DataIncorrectException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("un des données est incorrecte");
    }

    @ExceptionHandler(value = {DonneeNotFillException.class})
    public ResponseEntity<Object> donneeNotFillHandleError(DonneeNotFillException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("toutes les données ne sont pas remplit");
    }
}
