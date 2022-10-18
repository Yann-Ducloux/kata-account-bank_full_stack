package com.bank.account.service;

import com.bank.account.dto.AccountBankRequestDTO;
import com.bank.account.dto.AccountBankResponseDTO;
import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.entity.AccountBank;

import java.time.LocalDateTime;
import java.util.List;

public class AccountBankDataTest {
    static AccountBank buildDefaultAccountBank() {
        return AccountBank.builder()
                .id(1l)
                .solde(100l)
                .client(ClientDataTest.buildDefaultClientSaved())
                .dateCreation(LocalDateTime.of(24, 11, 10, 12, 0))
                .decouvert(1000l)
                .build();
    }
    static AccountBankResponseDTO  buildDefaultAccountBankResponseDTO() {
        return AccountBankResponseDTO.builder()
                .id(1l)
                .solde(100l)
                .client(ClientDataTest.buildDefaultClientResponse())
                .dateCreation(LocalDateTime.of(24, 11, 10, 12, 0))
                .decouvert(1000l)
                .build();
    }
    static AccountBankRequestDTO buildDefaultAccountBankRequestDTO() {
        return AccountBankRequestDTO.builder()
                .solde(100l)
                .decouvert(1000l)
                .build();
    }
    static AccountBankRequestDTO buildDefaultAccountBankRequestEmptySoldeDTO() {
        return AccountBankRequestDTO.builder()
                .decouvert(1000l)
                .build();
    }
    static AccountBankRequestDTO buildDefaultAccountBankRequestEmptyDecouvertDTO() {
        return AccountBankRequestDTO.builder()
                .solde(1000l)
                .build();
    }
    static AccountBank buildDefaultAccountBankEmptyDecouvert() {
        return AccountBank.builder()
                .solde(1000l)
                .client(ClientDataTest.buildDefaultClientSaved())
                .build();
    }
    static AccountBank buildDefaultAccountBankEmptySolde() {
        return AccountBank.builder()
                .decouvert(1000l)
                .client(ClientDataTest.buildDefaultClientSaved())
                .build();
    }
}
