package com.bank.account.service;

import com.bank.account.dto.AccountBankResponseDTO;
import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.dto.HistoriqueOperationDTO;
import com.bank.account.entity.Client;
import com.bank.account.exception.AccountBankHaveNotException;
import com.bank.account.exception.MailNotFillException;
import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.OperationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OperationServiceTest {
    @InjectMocks
    private static OperationService operationService;
    ModelMapper modelMapper = new ModelMapper();
    @Mock
    static AccountBankRepository accountBankRepository;
    @Mock
    static OperationRepository operationRepository;
    @BeforeAll
    static void setup() {
        operationService = new OperationService(operationRepository, accountBankRepository);
    }


    @Test
    void getHistoriqueValidTest() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildDefaultClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "ducloux.y@gmail.com";
        lenient().when(accountBankRepository.findByMail(mail)).thenReturn(List.of(AccountBankDataTest.buildDefaultAccountBank()));
        lenient().when(operationRepository.findByAccountBankId(1L)).thenReturn(List.of(OperationDataTest.buildDefaultOperation()));

        //WHEN
        List<HistoriqueOperationDTO> historiqueOperationDTOListAcutal = operationService.getHistorique(mail);
        //THEN
        assertEquals(historiqueOperationDTOListAcutal, List.of(OperationDataTest.buildDefaultHistoriqueOperation()));
    }
    @Test
    void getHistoriqueMailFailedTest() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildDefaultClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "";
        lenient().when(accountBankRepository.findByMail(mail)).thenReturn(List.of(AccountBankDataTest.buildDefaultAccountBank()));
        lenient().when(operationRepository.findByAccountBankId(1L)).thenReturn(List.of(OperationDataTest.buildDefaultOperation()));


        //WHEN
        Exception exception = assertThrows(MailNotFillException.class, () -> {
            List<HistoriqueOperationDTO> historiqueOperationDTOListAcutal = operationService.getHistorique(mail);
        });

        //THEN
        String expectedMessage = "le mail n'est pas remplit";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    void getHistoriqueAccountNotExistTest() {
        //GIVEN
        ClientResquestDTO clientResquestDTO = ClientDataTest.buildDefaultClient();
        Client client = modelMapper.map(clientResquestDTO, Client.class);
        client.setId(1L);
        String mail = "ducloux.y@gmail.com";
        lenient().when(accountBankRepository.findByMail(mail)).thenReturn(List.of());


        //WHEN
        Exception exception = assertThrows(AccountBankHaveNotException.class, () -> {
            List<HistoriqueOperationDTO> historiqueOperationDTOListAcutal = operationService.getHistorique(mail);
        });

        //THEN
        String expectedMessage = "l'utilisateur n'a pas de compte";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}