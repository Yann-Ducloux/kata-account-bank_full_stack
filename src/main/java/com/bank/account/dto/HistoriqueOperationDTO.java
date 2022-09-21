package com.bank.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoriqueOperationDTO {
    Long AccountBankid;
    private Long solde;
    private Long decouvert;
    private List<OperationLightDTO> operationLightDTO;
}
