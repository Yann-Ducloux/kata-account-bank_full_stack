package com.bank.account.controller;

import com.bank.account.dto.AccountBankDTO;
import com.bank.account.dto.AccountBankFullDTO;
import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.service.AccountBankService;
import com.bank.account.service.OperationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountBankController {
    private AccountBankService accountBankService;
    public AccountBankController(AccountBankService accountBankService) {
        this.accountBankService=accountBankService;
    }

    @PostMapping("/account_bank/create")
    AccountBankFullDTO nouveaucompte(@RequestBody AccountBankDTO accountBankDTO) {
        return this.accountBankService.saveAccountBank(accountBankDTO);
    }


    @PostMapping("/account_bank/find")
    List<AccountBankFullDTO> nouveauClient(@RequestBody String mail) {
        return this.accountBankService.getAllAccountBank(mail);
    }
}
