package com.bank.account.service;

import com.bank.account.dto.ClientResponseDTO;
import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.dto.ConnectionRequestDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.ClientPasswordFalseException;
import com.bank.account.exception.DonneeNotFillException;
import com.bank.account.exception.MailIsInvalidEception;
import com.bank.account.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AuthentificationServiceTest {
    @InjectMocks
    private static AuthentificationService authentificationService;
    ModelMapper modelMapper = new ModelMapper();
    @Mock
    static ClientRepository clientRepository;
    @BeforeAll
    static void setup() {
        authentificationService = new AuthentificationService(clientRepository);
    }

    @Test
    void saveAuthentification() {
        //GIVEN
        ConnectionRequestDTO connectionRequestDTO = ConnectionDataTest.buildDefaultConnection();
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.findByMail(mail)).thenReturn(Optional.of(ClientDataTest.buildDefaultClientSaved()));

        //WHEN
        Boolean booleanConnection = authentificationService.connection(connectionRequestDTO);

        //THEN
        assertTrue(booleanConnection);
    }

    @Test
    void saveAuthentificationMailNotExistThenThrowException() {
        //GIVEN
        ConnectionRequestDTO connectionRequestDTO = ConnectionDataTest.buildDefaultConnection();
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.findByMail(mail)).thenReturn(Optional.empty());

        //WHEN
        Exception exception = assertThrows(MailIsInvalidEception.class, () -> { authentificationService.connection(connectionRequestDTO); });
    }

    @Test
    void saveAuthentificationDataEmptyThenThrowException() {
        //GIVEN
        ConnectionRequestDTO connectionRequestDTO = ConnectionDataTest.buildEmptyConnection();
        String mail = "ducloux.y@gmail.com";

        //WHEN
        Exception exception = assertThrows(DonneeNotFillException.class, () -> {authentificationService.connection(connectionRequestDTO); });
    }
    @Test
    void saveAuthentificationPasswordFalseThenThrowException() {
        //GIVEN
        ConnectionRequestDTO connectionRequestDTO = ConnectionDataTest.buildDefaultConnection();
        String mail = "ducloux.y@gmail.com";
        lenient().when(clientRepository.findByMail(mail)).thenReturn(Optional.of(ClientDataTest.buildDefaultClientPAsswordFalseSaved()));

        //WHEN
        Exception exception = assertThrows(ClientPasswordFalseException.class, () -> { authentificationService.connection(connectionRequestDTO); });
    }
}