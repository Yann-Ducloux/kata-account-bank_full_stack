package com.bank.account.entity;

import com.bank.account.enumeration.TypeOperation;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Operation {

    private @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    AccountBank accountBank;
    TypeOperation somme;

}
