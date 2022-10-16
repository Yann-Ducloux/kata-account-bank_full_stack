package com.bank.account.controller;

import com.bank.account.dto.AccountBankDTO;
import com.bank.account.dto.AccountBankFullDTO;
import com.bank.account.service.AccountBankService;
import com.bank.account.service.DecodeJwtService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
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
    @PostMapping("/account_bank")
    @ApiOperation(value = "post a account bank for create by client conecte", notes = "create a account bank by client conecter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
    })
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
    @ApiOperation(value = "get a account by user connected", notes = "create a account bank")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The account bank was not found")
    })
    List<AccountBankFullDTO> nouveauClient( HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return this.accountBankService.getAllAccountBank(mail);
        }  catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }
}
