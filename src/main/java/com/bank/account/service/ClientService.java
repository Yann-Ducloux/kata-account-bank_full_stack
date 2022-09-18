package com.bank.account.service;

import com.bank.account.dto.ClientChangePassewordDTO;
import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.ClientNotFoundException;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ClientService {
    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<ClientDTO> getAllClient() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }
    public ClientDTO getClient(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            throw  new ClientNotFoundException(id);
        }
        return modelMapper.map(optionalClient.get(), ClientDTO.class);
    }
    public void deleteClient(Long id) {
        boolean boClientExists = clientRepository.existsById(id);
        if (!boClientExists) {
            throw  new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }

    public ClientDTO saveClient(ClientFullDTO clientFullDTO) {
        clientFullDTO.setPassword(hashPassword(clientFullDTO.getPassword()));
        Client client = modelMapper.map(clientFullDTO, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }

    public ClientDTO miseAjourClient(ClientChangePassewordDTO clientChangePassewordDTO, Long id){
       return this.clientRepository.findById(id)
                .map(client -> {
                    if(verifyHash(clientChangePassewordDTO.getOldPassword(),client.getPassword())) {
                        client.setMail(clientChangePassewordDTO.getMail());
                        client.setNom(clientChangePassewordDTO.getNom());
                        client.setPrenom(clientChangePassewordDTO.getPrenom());
                        if (!clientChangePassewordDTO.getPassword().isEmpty()) {
                            client.setPassword(hashPassword(clientChangePassewordDTO.getPassword()));
                        }
                        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
                    } else {
                        throw  new ClientPasswordFalseException();
                    }
                })
                .orElseGet(() -> {
                    throw  new ClientNotFoundException(id);
                });
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
    private String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
