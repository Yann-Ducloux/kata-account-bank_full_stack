package com.bank.account.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bank.account.dto.*;
import com.bank.account.service.ClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ClientController {
    private ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService=clientService;
    }

    /**
     * fonction qui crée un nouveau client
     * @param clientFullDTO le info du client a créer
     * @return ClientDTO le info du client créer
     * @throws Exception
     */
    @PostMapping("/client")
    @ApiOperation(value = "post a client", notes = "create a client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
    })
    ResponseEntity<ClientDTO> nouveauClient(/*@ParameterObject*/ @RequestBody ClientFullDTO clientFullDTO) throws Exception {
            return ResponseEntity.ok().body(this.clientService.saveClient(clientFullDTO));
    }
}
