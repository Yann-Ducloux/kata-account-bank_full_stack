package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountBankFullDTO {
    Long id;
    ClientResponseDTO client;
    private Long solde;
    private Long decouvert;
    LocalDateTime dateCreation;
}
