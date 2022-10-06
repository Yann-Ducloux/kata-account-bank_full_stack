package com.bank.account.dto;

import com.bank.account.entity.Client;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountBankDTO {
    @Parameter(name="solde", description = "le solde initiale du compte", example = "1216")
    private Long solde;
    @Parameter(name="decouvert", description = "le découvert initiale du compte", example = "121600")
    private Long decouvert;
}
