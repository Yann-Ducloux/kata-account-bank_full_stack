package com.bank.account.service;

import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    static final String PREFIXE_PASSWORD = "jklhdfgjkDFGKSL5654lmsdfgjklm";
    static final String SUFFIXE_PASSWORD = "kjlfghn425425fth564FGDFG";
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Cette fonction enregistre un nouveau client.
     * @param clientFullDTO données lié du client
     * @return ClientDTO le client enregistré.
     * @throws DonneeNotFillException
     * @throws MailExistException
     */
    public ClientDTO saveClient(ClientFullDTO clientFullDTO) {
        if(clientFullDTO.getMail() ==null || clientFullDTO.getMail().isEmpty() ||
                clientFullDTO.getNom() ==null || clientFullDTO.getNom().isEmpty() ||
                clientFullDTO.getPrenom() ==null || clientFullDTO.getPrenom().isEmpty() ||
                clientFullDTO.getPassword() ==null || clientFullDTO.getPassword().isEmpty()) {
            throw new DonneeNotFillException();
        }
        if (clientRepository.existsByMail(clientFullDTO.getMail())) {
            throw new MailExistException(clientFullDTO.getMail());
        }
        clientFullDTO.setPassword(hashPassword(clientFullDTO.getPassword()));
        Client client = modelMapper.map(clientFullDTO, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }

    /**
     * Cette fonction crypte le mot de passe.
     * @param password mot de passe
     * @return mot de passe crypté.
     */
    private String hashPassword(String password){
        password = PREFIXE_PASSWORD + password  + SUFFIXE_PASSWORD;
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
