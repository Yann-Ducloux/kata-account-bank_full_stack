package com.bank.account.dto;

import com.bank.account.entity.AccountBank;
import com.bank.account.enumeration.TypeOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationDTO {
    Long idAccountBank;
    Long somme;
    TypeOperation typeOperation;
}
