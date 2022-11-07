package com.bank.account.controller;

import com.bank.account.dto.*;
import com.bank.account.service.DecodeJwtService;
import com.bank.account.service.OperationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param operationRequestDTO info de l'opération a créer
     * @param request
     * @return RecuDTO reçu de l'opération
     * @throws Exception
     */
    @PostMapping("/operation")
    @ApiOperation(value = "post a operation by user", notes = "create a operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    ResponseEntity<RecuResponseDTO> nouvelleOperation(@RequestBody OperationRequestDTO operationRequestDTO, HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return ResponseEntity.ok().body(this.operationService.saveOperation(operationRequestDTO, mail));
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
    @ApiOperation(value = "get a historique of operation by user", notes = "list historique a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The historique was not found")
    })
    ResponseEntity<List<HistoriqueOperationDTO>> nouveauClient(HttpServletRequest request) throws Exception {
        try {
            String mail = decodeJwtService.decodeJWT(request.getHeader("Authorization")).get("sub");
            return ResponseEntity.ok().body(this.operationService.getHistorique(mail));
        }  catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }
}
