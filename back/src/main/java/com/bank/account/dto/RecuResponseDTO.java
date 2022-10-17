package com.bank.account.dto;

import com.bank.account.enumeration.TypeOperation;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class RecuResponseDTO {
    Long idAccountBank;
    Long somme;
    TypeOperation typeOperation;
    LocalDateTime dateOperation;
}
