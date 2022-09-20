package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountBank {

    private @Id
    @GeneratedValue
    Long id;
    @OneToOne
    Client client;
    Long solde;
    Long decouvert;
    LocalDateTime dateCreation;
}
