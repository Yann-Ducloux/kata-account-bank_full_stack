package com.bank.account.service;

import com.bank.account.dto.ConnectionDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.ClientMailExistException;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.exception.MailIsInvalidEception;
import com.bank.account.repository.ClientRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class AuthentificationService { private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    public AuthentificationService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public String connection(ConnectionDTO connectionDTO) {
        List<Client> clients = this.clientRepository.findByMail(connectionDTO.getMail());
        if (clients.isEmpty()) {
            throw new MailIsInvalidEception(connectionDTO.getMail());
        } else {
            Client client = clients.get(0);
            if(!verifyHash(connectionDTO.getPassword(),client.getPassword())){
                throw new ClientPasswordFalseException();
            } else {
                return Jwts.builder()
                        .setSubject(connectionDTO.getMail())
                        .setIssuedAt(Date.from(Instant.now()))
                        .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                        .signWith(
                                SignatureAlgorithm.HS512,
                                TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="))
                        .compact();
            }
        }
    }
    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

}
