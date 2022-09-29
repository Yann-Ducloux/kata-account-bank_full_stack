package com.bank.account.service;

import com.bank.account.dto.ConnectionDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.ClientMailExistException;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.exception.DonneeNotFillException;
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
import java.util.Optional;

@Service
public class AuthentificationService {
    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    public AuthentificationService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Boolean connection(ConnectionDTO connectionDTO) {
        if(connectionDTO.getMail() ==null || connectionDTO.getMail().isEmpty() ||
                connectionDTO.getPassword() ==null || connectionDTO.getPassword().isEmpty()) {
            throw new DonneeNotFillException();
        }
        Optional<Client> clients = this.clientRepository.findByMail(connectionDTO.getMail());
        if (!clients.isPresent()) {
            throw new MailIsInvalidEception(connectionDTO.getMail());
        }
        Client client = clients.get();
        if(!verifyHash(connectionDTO.getPassword(),client.getPassword())){
            throw new ClientPasswordFalseException();
        }
        return true;
    }
    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

}
