package com.bank.account.service;

import com.bank.account.dto.AccountBankResponseDTO;
import com.bank.account.dto.ClientResponseDTO;
import com.bank.account.dto.ConnectionRequestDTO;
import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountBankServiceTest {
    @InjectMocks
    private static AccountBankService accountBankService;
    ModelMapper modelMapper = new ModelMapper();
    @Mock
    static AccountBankRepository accountBankRepository;
    @Mock
    static ClientRepository clientRepository;
    @BeforeAll
    static void setup() {
        accountBankService = new AccountBankService(accountBankRepository, clientRepository);
    }

    @Test
    void getAllAccountBankTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;
        lenient().when(accountBankRepository.findByMail(mail)).thenReturn(List.of(AccountBankDataTest.buildDefaultAccountBank()));
        //WHEN
        List<AccountBankResponseDTO> accountBankResponseDTO = accountBankService.getAllAccountBank(mail);

        //THEN
        assertEquals(sizeList, accountBankResponseDTO.size());
        assertEquals(accountBankResponseDTO.get(0), AccountBankDataTest.buildDefaultAccountBankResponseDTO());
    }

    @Test
    void getAllAccountBankMailEmptyTest() {
        //GIVEN
        String mail = "";
        //WHEN

        Exception exception = assertThrows(MailNotFillException.class, () -> {
            List<AccountBankResponseDTO> accountBankResponseDTO = accountBankService.getAllAccountBank(mail);
        });

        String expectedMessage = "le mail n'est pas remplit";
        String actualMessage = exception.getMessage();

        //THEN
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveAccountBankTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;
        lenient().when(accountBankRepository.save(any(AccountBank.class))).thenReturn(AccountBankDataTest.buildDefaultAccountBank());
        lenient().when(clientRepository.findByMail(mail)).thenReturn(Optional.of(ClientDataTest.buildDefaultClientSaved()));

        //WHEN
        AccountBankResponseDTO accountBankResponseDTO = accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestDTO(), mail);

        //THEN
        assertEquals(AccountBankDataTest.buildDefaultAccountBankResponseDTO(), accountBankResponseDTO);
    }

    @Test
    void saveAccountBankEmptyDecouvertTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;

        //WHEN
        Exception exception = assertThrows(DecouvertException.class, () -> {
            AccountBankResponseDTO accountBankResponseDTO = accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestEmptyDecouvertDTO(), mail);
        });

        String expectedMessage = "decouvert erreur";
        String actualMessage = exception.getMessage();

        //THEN
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveAccountBankEmptySoldeTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;

        //WHEN
        Exception exception = assertThrows(SoldeException.class, () -> {
            AccountBankResponseDTO accountBankResponseDTO = accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestEmptySoldeDTO(), mail);
        });

        String expectedMessage = "solde erreur";
        String actualMessage = exception.getMessage();

        //THEN
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void saveAccountBankClientEmptyTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;
        lenient().when(accountBankRepository.save(any(AccountBank.class))).thenReturn(AccountBankDataTest.buildDefaultAccountBank());
        lenient().when(clientRepository.findByMail(mail)).thenReturn(Optional.empty());

        //WHEN
        Exception exception = assertThrows(ClientMailExistException.class, () -> {
            AccountBankResponseDTO accountBankResponseDTO = accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestDTO(), mail);
        });

        String expectedMessage = "le mail "+ mail + " n'existe pas";
        String actualMessage = exception.getMessage();

        //THEN
        assertTrue(actualMessage.contains(expectedMessage));

    }

}