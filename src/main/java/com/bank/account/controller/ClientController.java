package com.bank.account.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bank.account.dto.AccountBankDTO;
import com.bank.account.dto.ClientChangePassewordDTO;
import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
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


    @PostMapping("/connection")
    String connection(@RequestBody ClientFullDTO clientFullDTO) {
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime= localDateTime.plusDays(1);
        String jwt = Jwts.builder()
                .setSubject("ducloux.y@gmail.com")
                .claim("name", "Micah Silverman")
                .claim("scope", "admins")
                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                .setIssuedAt(Date.from(Instant.now()))
                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(
                        SignatureAlgorithm.HS512,
                        TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                .compact();
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        final Map<String, String> jsonMap; // = new HashMap<String, Object>();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert JSON string to Map
            jsonMap = mapper.readValue(payload,
                    new TypeReference<Map<String, String>>() {
                    });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return jwt;
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
