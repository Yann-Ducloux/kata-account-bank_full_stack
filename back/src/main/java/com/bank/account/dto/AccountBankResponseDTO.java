package com.bank.account.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AccountBankResponseDTO {
    Long id;
    ClientResponseDTO client;
    private Long solde;
    private Long decouvert;
    LocalDateTime dateCreation;
}
