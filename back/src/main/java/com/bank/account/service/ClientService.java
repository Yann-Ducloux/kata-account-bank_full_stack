package com.bank.account.service;

import com.bank.account.dto.ClientResponseDTO;
import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
import com.bank.account.repository.ClientRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class ClientService {

    ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    static final String PREFIXE_PASSWORD = "jklhdfgjkDFGKSL5654lmsdfgjklm";
    static final String SUFFIXE_PASSWORD = "kjlfghn425425fth564FGDFG";
    static final String REGEX_MAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Cette fonction enregistre un nouveau client.
     * @param clientResquestDTO données lié du client
     * @return ClientDTO le client enregistré.
     * @throws DonneeNotFillException
     * @throws MailExistException
     */
    public ClientResponseDTO saveClient(ClientResquestDTO clientResquestDTO) {
        controleChamps(clientResquestDTO);
        clientResquestDTO.setPassword(hashPassword(clientResquestDTO.getPassword()));
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        Client clientSaved = clientRepository.save(client);
        return modelMapper.map(clientSaved, ClientResponseDTO.class);
    }
    private void controleChamps(ClientResquestDTO clientResquestDTO) {
        Pattern pattern = Pattern.compile(REGEX_MAIL);
        if(clientResquestDTO.getMail() ==null || clientResquestDTO.getMail().isEmpty() ||
                clientResquestDTO.getNom() ==null || clientResquestDTO.getNom().isEmpty() ||
                clientResquestDTO.getPrenom() ==null || clientResquestDTO.getPrenom().isEmpty() ||
                clientResquestDTO.getPassword() ==null || clientResquestDTO.getPassword().isEmpty()) {
            throw new DonneeNotFillException();
        }
        if(!pattern.matcher(clientResquestDTO.getMail()).matches() ||
            clientResquestDTO.getMail().length() <6 || clientResquestDTO.getMail().length() >30 ||
            clientResquestDTO.getNom().length() <2 || clientResquestDTO.getNom().length() >30 ||
            clientResquestDTO.getPrenom().length() <2 || clientResquestDTO.getPrenom().length() >30 ||
            clientResquestDTO.getPassword().length() <6 || clientResquestDTO.getPassword().length() >30) {
            throw new DataIncorrectException();
        }
        if(clientRepository.existsByMail(clientResquestDTO.getMail())) {
            throw new MailExistException(clientResquestDTO.getMail());
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
