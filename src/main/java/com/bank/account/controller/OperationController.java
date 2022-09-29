package com.bank.account.controller;

import com.bank.account.dto.*;
import com.bank.account.service.DecodeJwtService;
import com.bank.account.service.OperationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin()
public class OperationController {
    private OperationService operationService;
    private DecodeJwtService decodeJwtService;

    public OperationController(OperationService operationService, DecodeJwtService decodeJwtService) {

        this.operationService=operationService;
        this.decodeJwtService=decodeJwtService;
    }
    @PostMapping("/operation")
    RecuDTO nouvelleOperation(@RequestBody OperationDTO operationDTO, HttpServletRequest request) {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return this.operationService.saveOperation(operationDTO, mail);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
    @GetMapping("/historique")
    List<HistoriqueOperationDTO> nouveauClient(HttpServletRequest request) {
        String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
        return this.operationService.getHistorique(mail);
    }
}
