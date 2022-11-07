package com.bank.account.service;

import com.bank.account.dto.AccountBankResponseDTO;
import com.bank.account.entity.AccountBank;
import com.bank.account.exception.*;
import com.bank.account.repository.AccountBankRepository;
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


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    void getAllAccountBank() {
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
    void getAllAccountBankMailEmptyThenThrowException() {
        //GIVEN
        String mail = "";

        //WHEN
        assertThrows(MailNotFillException.class, () -> { accountBankService.getAllAccountBank(mail); });
    }

    @Test
    void saveAccountBank() {
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
    void saveAccountBankEmptyDecouvertThenThrowException() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;

        //WHEN
        assertThrows(DecouvertException.class, () -> { accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestEmptyDecouvertDTO(), mail);});
    }

    @Test
    void saveAccountBankEmptySoldeThenThrowException() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;

        //WHEN
        assertThrows(SoldeException.class, () -> { accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestEmptySoldeDTO(), mail); });
    }

    @Test
    void saveAccountBankClientEmptyThenThrowException() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        int sizeList = 1;
        lenient().when(accountBankRepository.save(any(AccountBank.class))).thenReturn(AccountBankDataTest.buildDefaultAccountBank());
        lenient().when(clientRepository.findByMail(mail)).thenReturn(Optional.empty());

        //WHEN
        assertThrows(ClientMailExistException.class, () -> {accountBankService.saveAccountBank(AccountBankDataTest.buildDefaultAccountBankRequestDTO(), mail); });

    }

}