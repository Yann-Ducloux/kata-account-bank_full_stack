package com.bank.account.service;

import com.bank.account.dto.ClientChangePassewordDTO;
import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.dto.ConnectionDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.ClientMailExistException;
import com.bank.account.exception.ClientNotFoundException;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.repository.ClientRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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
        if (clientRepository.existsByMail(clientFullDTO.getMail())) {
            throw new ClientMailExistException(clientFullDTO.getMail());
        }
        clientFullDTO.setPassword(hashPassword(clientFullDTO.getPassword()));
        Client client = modelMapper.map(clientFullDTO, Client.class);
        client = clientRepository.save(client);
        return modelMapper.map(clientRepository.save(client), ClientDTO.class);
    }
    public String connection(ConnectionDTO connectionDTO) {
        List<Client> clients = this.clientRepository.findByMail(connectionDTO.getMail());
        if (clients.isEmpty()) {
            throw new ClientMailExistException(connectionDTO.getMail());
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
