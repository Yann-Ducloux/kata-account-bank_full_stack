package com.bank.account.controller;

import com.bank.account.dto.OperationDTO;
import com.bank.account.dto.RecuDTO;
import com.bank.account.exception.ClientNotFoundException;
import com.bank.account.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class OperationController {
    private OperationService operationService;
    public OperationController(OperationService operationService) {
        this.operationService=operationService;
    }
    @PostMapping("/operation")
    RecuDTO nouvelleOperation(@RequestBody OperationDTO operationDTO) {
        try {
            return this.operationService.saveOperation(operationDTO);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
