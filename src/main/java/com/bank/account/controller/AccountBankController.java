package com.bank.account.controller;

import com.bank.account.dto.AccountBankDTO;
import com.bank.account.dto.AccountBankFullDTO;
import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.service.AccountBankService;
import com.bank.account.service.DecodeJwtService;
import com.bank.account.service.OperationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin()
public class AccountBankController extends HandlerInterceptorAdapter {
    private AccountBankService accountBankService;
    private DecodeJwtService decodeJwtService;

    public AccountBankController(AccountBankService accountBankService, DecodeJwtService decodeJwtService) {
        this.accountBankService=accountBankService;
        this.decodeJwtService=decodeJwtService;

    }

    /**
     * fonction qu crée un compte en bank
     * @param accountBankDTO info du compte en bank a créer
     * @param request
     * @return accountBank info du compte en bank créer
     * @throws Exception
     */
    @PostMapping("/account_bank/create")
    AccountBankFullDTO nouveaucompte(@RequestBody AccountBankDTO accountBankDTO,  HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return this.accountBankService.saveAccountBank(accountBankDTO, mail);
        }  catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }

    /**
     * fonction qui renvoie la liste du compte en bank de la personne
     * @param request
     * @return List<AccountBankFullDTO> la liste des infos du compte en bank créer
     * @throws Exception
     */
    @GetMapping({ "/account_bank/find" })
    List<AccountBankFullDTO> nouveauClient( HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return this.accountBankService.getAllAccountBank(mail);
        }  catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }
}
