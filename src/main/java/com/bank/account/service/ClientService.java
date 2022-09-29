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
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

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
        client = clientRepository.save(client);
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }

    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
