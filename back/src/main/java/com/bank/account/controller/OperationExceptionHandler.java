package com.bank.account.controller;

import com.bank.account.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OperationExceptionHandler {

    @ExceptionHandler(value = {OperationDonneManquanteExcepion.class, OperationSommeNulException.class})
    public ResponseEntity<Object> notFillHandleError(OperationDonneManquanteExcepion ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("toutes les données ne sont pas remplit");
    }
    @ExceptionHandler(value = {ClientMailNotEqualsException.class, DecouvertPlafondException.class, TypeOperationNotExistException.class, AccountBankNotExistException.class, AccountBankHaveNotException.class})
    public ResponseEntity<Object> incorrectDataHandleError(OperationDonneManquanteExcepion ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Donnée incorrecte");
    }
}
