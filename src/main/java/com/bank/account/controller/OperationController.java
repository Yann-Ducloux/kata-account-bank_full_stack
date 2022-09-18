package com.bank.account.controller;

import com.bank.account.service.ClientService;
import com.bank.account.service.OperationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {
    private OperationService operationService;
    public OperationController(OperationService operationService) {
        this.operationService=operationService;
    }
}
