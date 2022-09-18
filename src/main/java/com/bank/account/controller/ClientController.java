package com.bank.account.controller;

import java.util.List;

import com.bank.account.dto.ClientChangePassewordDTO;
import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.exception.ClientNotFoundException;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ClientController {
    private ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService=clientService;
    }

    @GetMapping("/clients")
    List<ClientDTO> getAllClient() {
        return this.clientService.getAllClient();
    }


    @GetMapping("/clients/{id}")
    ClientDTO getClientById(@PathVariable Long id) {
        try {
        return this.clientService.getClient(id);
        } catch (ClientNotFoundException exception) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "clients non trouvé", exception);
       }
    }
    @PostMapping("/client")
    ClientDTO nouveauClient(@RequestBody ClientFullDTO clientFullDTO) {
        return this.clientService.saveClient(clientFullDTO);
    }

    @DeleteMapping("/client/{id}")
    void deleteClient(@PathVariable Long id) {
        try {
            this.clientService.deleteClient(id);
        } catch (ClientNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "clients non trouvé", exception);
        }
    }

    @PutMapping("/client/{id}")
    ClientDTO miseAjourClient(@RequestBody ClientChangePassewordDTO clientChangePassewordDTO, @PathVariable Long id) {
        try {
            return this.clientService.miseAjourClient(clientChangePassewordDTO, id);
        } catch (ClientNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "clients non trouvé", exception);
        } catch (ClientPasswordFalseException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "mot de passe faux", exception);
        }
    }
}
