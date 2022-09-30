package com.bank.account.controller;

import com.bank.account.dto.*;
import com.bank.account.exception.MailNotFillException;
import com.bank.account.exception.OperationDonneManquanteExcepion;
import com.bank.account.exception.OperationSommeNulException;
import com.bank.account.exception.TypeOperationNotExistException;
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

    /**
     * fonction qui effectue un opération
     * @param operationDTO info de l'opération a créer
     * @param request
     * @return RecuDTO reçu de l'opération
     * @throws Exception
     */
    @PostMapping("/operation")
    RecuDTO nouvelleOperation(@RequestBody OperationDTO operationDTO, HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return this.operationService.saveOperation(operationDTO, mail);
        } catch (Exception exception) {
            throw new Exception("USER_DISABLED", exception);
        }
    }

    /**
     *  fonction qui récupére la l'historique de toutes les opération
     * @param request
     * @return  List<HistoriqueOperationDTO> la liste des infos des opération
     * @throws Exception
     */
    @GetMapping("/historique")
    List<HistoriqueOperationDTO> nouveauClient(HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return this.operationService.getHistorique(mail);
        }  catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }
}
