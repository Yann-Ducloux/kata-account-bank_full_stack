package com.bank.account.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class HistoriqueOperationDTO {
    Long AccountBankid;
    private Long solde;
    private Long decouvert;
    private List<OperationLightDTO> operationLightDTO;
}
