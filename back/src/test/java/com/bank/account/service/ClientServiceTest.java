package com.bank.account.service;

import com.bank.account.dto.ClientResponseDTO;
import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.DataIncorrectException;
import com.bank.account.exception.DonneeNotFillException;
import com.bank.account.exception.MailExistException;
import com.bank.account.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ClientServiceTest {
    @InjectMocks
    private static ClientService clientService;
    ModelMapper modelMapper = new ModelMapper();
    @Mock
    static ClientRepository clientRepository;
    @BeforeAll
    static void setup() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    void saveClientValidTest() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildDefaultClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.existsByMail(mail)).thenReturn(false);
        lenient().when(clientRepository.save(any(Client.class))).thenReturn(client);

        //WHEN
        ClientResponseDTO clientResponseDTO = clientService.saveClient(clientResquestDTO);

        //THEN
        assertThat(clientResponseDTO.getMail()).isEqualTo(mail);
    }
    @Test()
    void saveEmptyClientThenThrowException() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildEmptyClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.existsByMail(mail)).thenReturn(false);
        lenient().when(clientRepository.save(any(Client.class))).thenReturn(client);

        //WHEN
        assertThrows(DonneeNotFillException.class, () -> {  clientService.saveClient(clientResquestDTO); });
    }
    @Test
    void saveClientPasswordShortThenThrowException() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildPasswordShortClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.existsByMail(mail)).thenReturn(false);
        lenient().when(clientRepository.save(any(Client.class))).thenReturn(client);

        //WHEN
        assertThrows(DataIncorrectException.class, () -> { clientService.saveClient(clientResquestDTO);  });
    }
    @Test
    void saveMailExistClientThenThrowException() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildDefaultClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.existsByMail(mail)).thenReturn(true);
        lenient().when(clientRepository.save(any(Client.class))).thenReturn(client);

        //WHEN
        Exception exception = assertThrows(MailExistException.class, () -> { clientService.saveClient(clientResquestDTO); });
    }
}