package com.bank.account.service;

import com.bank.account.dto.HistoriqueOperationDTO;
import com.bank.account.dto.OperationLightDTO;
import com.bank.account.dto.OperationRequestDTO;
import com.bank.account.dto.RecuResponseDTO;
import com.bank.account.entity.Operation;
import com.bank.account.enumeration.TypeOperation;

import java.time.LocalDateTime;
import java.util.List;

public class OperationDataTest {
    static Operation buildDefaultOperation() {
        return Operation.builder()
                .accountBank(AccountBankDataTest.buildDefaultAccountBank())
                .id(1L)
                .somme(1000l)
                .typeOperation(TypeOperation.DEPOSIT)
                .dateOperation(LocalDateTime.of(24, 11, 10, 12, 0))
                .build();
    }
    static OperationRequestDTO buildDefaultOperationRequest() {
        return OperationRequestDTO.builder()
                .idAccountBank(1L)
                .somme(1000l)
                .typeOperation(TypeOperation.DEPOSIT)
                .build();
    }
    static OperationRequestDTO buildDefaultOperationSommeNullRequest() {
        return OperationRequestDTO.builder()
                .idAccountBank(1L)
                .typeOperation(TypeOperation.DEPOSIT)
                .build();
    }
    static OperationRequestDTO buildDefaultOperationSommeNegatifRequest() {
        return OperationRequestDTO.builder()
                .idAccountBank(1L)
                .somme(-1000l)
                .typeOperation(TypeOperation.DEPOSIT)
                .build();
    }
    static OperationRequestDTO buildDefaultOperationRetirerRequest() {
        return OperationRequestDTO.builder()
                .idAccountBank(1L)
                .somme(100l)
                .typeOperation(TypeOperation.WITHDRAWAL)
                .build();
    }
    static OperationRequestDTO buildDefaultOperationRetirerGrandeSommeRequest() {
        return OperationRequestDTO.builder()
                .idAccountBank(1L)
                .somme(10000l)
                .typeOperation(TypeOperation.WITHDRAWAL)
                .build();
    }
    static RecuResponseDTO buildDefaultRecuResponse() {
        return RecuResponseDTO.builder()
                .idAccountBank(1L)
                .somme(1000l)
                .typeOperation(TypeOperation.DEPOSIT)
                .dateOperation(LocalDateTime.of(24, 11, 10, 12, 0))
                .build();
    }

    static HistoriqueOperationDTO buildDefaultHistoriqueOperation() {
        return HistoriqueOperationDTO.builder()
                .solde(100L)
                .AccountBankid(1L)
                .decouvert(1000l)
                .operationLightDTO(List.of(buildDefaultoperationLight()))
                .build();
    }
    static OperationLightDTO buildDefaultoperationLight() {
        return OperationLightDTO.builder()
                .somme(1000L)
                .dateOperation(LocalDateTime.of(24, 11, 10, 12, 0))
                .typeOperation((TypeOperation.DEPOSIT))
                .build();

    }
}
