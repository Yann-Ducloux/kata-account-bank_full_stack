package com.bank.account.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class DecodeJwtService {

    public Map<String, String> decodeJWT(String jwt) {

        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload,
                    new TypeReference<Map<String, String>>() {
                    });
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
