package com.bank.account.service;

import com.bank.account.dto.AccountBankResponseDTO;
import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.dto.HistoriqueOperationDTO;
import com.bank.account.dto.RecuResponseDTO;
import com.bank.account.entity.Client;
import com.bank.account.entity.Operation;
import com.bank.account.exception.*;
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
import java.util.Optional;

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
    void getHistoriqueValid() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        Long numeroCompte = 1L;
        lenient().when(accountBankRepository.findByMailAndAccountBankId(mail, numeroCompte)).thenReturn(Optional.of(AccountBankDataTest.buildDefaultAccountBank()));
        lenient().when(operationRepository.findByAccountBankId(numeroCompte)).thenReturn(List.of(OperationDataTest.buildDefaultOperation()));

        //WHEN
        HistoriqueOperationDTO historiqueOperationDTOAcutal = operationService.getHistorique(mail, numeroCompte);

        //THEN
        assertEquals(historiqueOperationDTOAcutal, OperationDataTest.buildDefaultHistoriqueOperation());
    }
    @Test
    void getHistoriqueMailFailedThenThrowException() {
        //GIVEN
        String mail = "";
        Long numeroCompte = 1L;
        lenient().when(accountBankRepository.findByMail(mail)).thenReturn(List.of(AccountBankDataTest.buildDefaultAccountBank()));
        lenient().when(operationRepository.findByAccountBankId(1L)).thenReturn(List.of(OperationDataTest.buildDefaultOperation()));


        //WHEN
        assertThrows(MailNotFillException.class, () -> { operationService.getHistorique(mail, numeroCompte); });

    }
    @Test
    void getHistoriqueAccountNotExistThenThrowException() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        Long numeroCompte = 1L;
        lenient().when(accountBankRepository.findByMail(mail)).thenReturn(List.of());


        //WHEN
        assertThrows(AccountBankErrorException.class, () -> {operationService.getHistorique(mail, numeroCompte);});
    }
    @Test
    void saveOperationValid() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        lenient().when(accountBankRepository.findById(1L)).thenReturn(Optional.of(AccountBankDataTest.buildDefaultAccountBank()));
        lenient().when(operationRepository.save(any(Operation.class))).thenReturn(OperationDataTest.buildDefaultOperation());

        //WHEN
        RecuResponseDTO recuResponseDTO = operationService.saveOperation(OperationDataTest.buildDefaultOperationRequest(), mail);

        //THEN
        assertEquals(recuResponseDTO, OperationDataTest.buildDefaultRecuResponse());
    }
    @Test
    void saveOperationSommeMailNullThenThrowException() {
        //GIVEN
        String mail = "";

        //WHEN
        Exception exception = assertThrows(MailNotFillException.class, () -> { operationService.saveOperation(OperationDataTest.buildDefaultOperationSommeNullRequest(), mail);});
    }
    @Test
    void saveOperationSommeNullThenThrowException() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";

        //WHEN
        Exception exception = assertThrows(OperationDonneManquanteExcepion.class, () -> { operationService.saveOperation(OperationDataTest.buildDefaultOperationSommeNullRequest(), mail); });
    }
    @Test
    void saveOperationDecouvertFailedTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        Long Somme = -1000L;
        lenient().when(accountBankRepository.findById(1L)).thenReturn(Optional.of(AccountBankDataTest.buildDefaultAccountBankEmptyDecouvert()));

        //WHEN
        Exception exception = assertThrows(DecouvertException.class, () -> { operationService.saveOperation(OperationDataTest.buildDefaultOperationRetirerRequest(), mail); });
    }
    @Test
    void saveOperationSoldeFailedTest() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        Long Somme = -1000L;
        lenient().when(accountBankRepository.findById(1L)).thenReturn(Optional.of(AccountBankDataTest.buildDefaultAccountBankEmptySolde()));

        //WHEN
        Exception exception = assertThrows(SoldeException.class, () -> { operationService.saveOperation(OperationDataTest.buildDefaultOperationRetirerRequest(), mail); });
    }
    @Test
    void saveOperationDecouvertPlafondFailedThenThrowException() {
        //GIVEN
        String mail = "ducloux.y@gmail.com";
        Long Somme = -1000L;
        lenient().when(accountBankRepository.findById(1L)).thenReturn(Optional.of(AccountBankDataTest.buildDefaultAccountBank()));

        //WHEN
        Exception exception = assertThrows(DecouvertPlafondException.class, () -> {operationService.saveOperation(OperationDataTest.buildDefaultOperationRetirerGrandeSommeRequest(), mail); });
    }
}