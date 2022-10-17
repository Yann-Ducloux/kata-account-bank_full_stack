package com.bank.account.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AccountBankRequestDTO {
    @Parameter(name="solde", description = "le solde initiale du compte", example = "1216")
    private Long solde;
    @Parameter(name="decouvert", description = "le découvert initiale du compte", example = "121600")
    private Long decouvert;
}
