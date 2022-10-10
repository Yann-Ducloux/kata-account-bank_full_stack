package com.bank.account.service;

import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    static final String PREFIXE_PASSWORD = "jklhdfgjkDFGKSL5654lmsdfgjklm";
    static final String SUFFIXE_PASSWORD = "kjlfghn425425fth564FGDFG";
    static final String REGEX_MAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
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
        controleChamps(clientFullDTO);
        clientFullDTO.setPassword(hashPassword(clientFullDTO.getPassword()));
        Client client = modelMapper.map(clientFullDTO, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }
    private void controleChamps(ClientFullDTO clientFullDTO) {
        Pattern pattern = Pattern.compile(REGEX_MAIL);
        if(clientFullDTO.getMail() ==null || clientFullDTO.getMail().isEmpty() ||
                clientFullDTO.getNom() ==null || clientFullDTO.getNom().isEmpty() ||
                clientFullDTO.getPrenom() ==null || clientFullDTO.getPrenom().isEmpty() ||
                clientFullDTO.getPassword() ==null || clientFullDTO.getPassword().isEmpty()) {
            throw new DonneeNotFillException();
        }
        if(!pattern.matcher(clientFullDTO.getMail()).matches() ||
            clientFullDTO.getMail().length() <6 || clientFullDTO.getMail().length() >30 ||
            clientFullDTO.getNom().length() <2 || clientFullDTO.getNom().length() >30 ||
            clientFullDTO.getPrenom().length() <2 || clientFullDTO.getPrenom().length() >30 ||
            clientFullDTO.getPassword().length() <6 || clientFullDTO.getPassword().length() >30) {
            throw new DataIncorrectException();
        }
        if(clientRepository.existsByMail(clientFullDTO.getMail())) {
            throw new MailExistException(clientFullDTO.getMail());
        }
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
