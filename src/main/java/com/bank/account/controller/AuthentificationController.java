package com.bank.account.controller;

import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ConnectionDTO;
import com.bank.account.service.AuthentificationService;
import com.bank.account.service.ClientService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
public class AuthentificationController {



    private AuthentificationService authentificationService;
    public AuthentificationController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @PostMapping("/connection")
    String connection(@RequestBody ConnectionDTO connectionDTO) {
        String jwt = this.authentificationService.connection(connectionDTO);
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        final Map<String, String> jsonMap; // = new HashMap<String, Object>();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        try {
            jsonMap = mapper.readValue(payload,
                    new TypeReference<Map<String, String>>() {
                    });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return jwt;
    }
}
