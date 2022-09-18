package com.bank.account.entity;

import com.bank.account.enumeration.TypeOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Operation {

    private @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    AccountBank accountBank;
    Long somme;
    TypeOperation typeOperation;
}
