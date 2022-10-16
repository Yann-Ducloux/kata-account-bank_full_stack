package com.bank.account.controller;

import com.bank.account.dto.*;
import com.bank.account.service.ClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
     * @param clientResquestDTO le info du client a créer
     * @return ClientDTO le info du client créer
     * @throws Exception
     */
    @PostMapping("/client")
    @ApiOperation(value = "post a client", notes = "create a client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
    })
    ResponseEntity<ClientResponseDTO> nouveauClient(/*@ParameterObject*/ @RequestBody ClientResquestDTO clientResquestDTO) throws Exception {
            return ResponseEntity.ok().body(this.clientService.saveClient(clientResquestDTO));
    }
}
