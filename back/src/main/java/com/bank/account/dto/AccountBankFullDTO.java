package com.bank.account.dto;

import com.bank.account.entity.Client;
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
    ClientDTO client;
    private Long solde;
    private Long decouvert;
    LocalDateTime dateCreation;
}
