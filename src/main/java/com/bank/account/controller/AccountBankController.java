package com.bank.account.controller;

import com.bank.account.service.AccountBankService;
import com.bank.account.service.OperationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountBankController {
    private AccountBankService accountBankService;
    public AccountBankController(AccountBankService accountBankService) {
        this.accountBankService=accountBankService;
    }
}
