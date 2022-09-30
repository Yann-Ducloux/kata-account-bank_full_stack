package com.bank.account.service;

import com.bank.account.dto.ConnectionDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
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

    static final String PREFIXE_PASSWORD = "jklhdfgjkDFGKSL5654lmsdfgjklm";
    static final String SUFFIXE_PASSWORD = "kjlfghn425425fth564FGDFG";
    /**
     * fonction qui retourne le bearer pour se connecté
     * @param connectionDTO mail et le mot de passe
     * @return le jwt qui permet de ce connecté
     * @throws DonneeNotFillException
     * @throws MailIsInvalidEception
     * @throws ClientPasswordFalseException
     */
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

    /**
     * Cette fonction vérifie si le mot de passe est correcte
     * @param password le mot de passe à vérifié non crypté
     * @param hash le mot de passe crypté
     * @return boolean true si les mot de passe sont identique
     */
    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(PREFIXE_PASSWORD+password+SUFFIXE_PASSWORD, hash);
    }

}
