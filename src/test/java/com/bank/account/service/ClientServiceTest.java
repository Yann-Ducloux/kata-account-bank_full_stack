package com.bank.account.service;

import com.bank.account.dto.ClientDTO;
import com.bank.account.dto.ClientFullDTO;
import com.bank.account.entity.Client;
import com.bank.account.repository.ClientRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private static ClientService clientService;

    ModelMapper modelMapper = new ModelMapper();
    @Mock
    static ClientRepository clientRepository;
    @BeforeAll
    static void setup() {
        clientService = new ClientService(clientRepository);
    }
    @Test
    void saveClient() {
        ClientFullDTO clientFullDTO = new ClientFullDTO();
        String mail = "ducloux.y@gmail.com";
        clientFullDTO.setMail(mail);
        clientFullDTO.setPrenom("Yann");
        clientFullDTO.setNom("Ducloux");
        clientFullDTO.setPassword("bjklhdfbjlnkklbgdnf");
        Client client = modelMapper.map(clientFullDTO, Client.class);
        client.setId(1L);
        lenient().when(clientRepository.existsByMail(mail)).thenReturn(false);
        lenient().when(clientRepository.save(any(Client.class))).thenReturn(client);
        ClientDTO clientDTO = clientService.saveClient(clientFullDTO);
        //System.out.println(clientDTO.getMail());
    }
}