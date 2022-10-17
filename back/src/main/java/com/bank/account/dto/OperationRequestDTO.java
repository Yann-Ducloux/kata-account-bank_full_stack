package com.bank.account.dto;

import com.bank.account.enumeration.TypeOperation;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class OperationRequestDTO {
    Long idAccountBank;
    Long somme;
    TypeOperation typeOperation;
}
