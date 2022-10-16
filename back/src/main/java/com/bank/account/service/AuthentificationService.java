package com.bank.account.service;

import com.bank.account.dto.ConnectionRequestDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
     * @param connectionRequestDTO mail et le mot de passe
     * @return le jwt qui permet de ce connecté
     * @throws DonneeNotFillException
     * @throws MailIsInvalidEception
     * @throws ClientPasswordFalseException
     */
    public Boolean connection(ConnectionRequestDTO connectionRequestDTO) {
        if(connectionRequestDTO.getMail() ==null || connectionRequestDTO.getMail().isEmpty() ||
                connectionRequestDTO.getPassword() ==null || connectionRequestDTO.getPassword().isEmpty()) {
            throw new DonneeNotFillException();
        }
        Optional<Client> clients = this.clientRepository.findByMail(connectionRequestDTO.getMail());
        if (!clients.isPresent()) {
            throw new MailIsInvalidEception(connectionRequestDTO.getMail());
        }
        Client client = clients.get();
        if(!verifyHash(connectionRequestDTO.getPassword(),client.getPassword())){
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
