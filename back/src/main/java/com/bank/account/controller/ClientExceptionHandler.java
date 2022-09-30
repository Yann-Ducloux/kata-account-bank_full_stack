package com.bank.account.controller;

import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.exception.DonneeNotFillException;
import com.bank.account.exception.MailExistException;
import org.springdoc.api.ErrorMessage;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(value
            = { MailExistException.class})
    public ResponseEntity<Object> handleError(MailExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("le mail existe dèjà");
    }
}
