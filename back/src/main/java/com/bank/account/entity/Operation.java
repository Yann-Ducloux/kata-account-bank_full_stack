package com.bank.account.entity;

import com.bank.account.enumeration.TypeOperation;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Operation {

    private @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    AccountBank accountBank;
    Long somme;
    TypeOperation typeOperation;
    LocalDateTime dateOperation;
}
