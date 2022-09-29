package com.bank.account.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bank.account.dto.*;
import com.bank.account.exception.ClientNotFoundException;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.service.ClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class ClientController {
    private ClientService clientService;
    public ClientController(ClientService clientService) {
        this.clientService=clientService;
    }


    @PostMapping("/client")
    ClientDTO nouveauClient(@RequestBody ClientFullDTO clientFullDTO) {
        return this.clientService.saveClient(clientFullDTO);
    }
}
