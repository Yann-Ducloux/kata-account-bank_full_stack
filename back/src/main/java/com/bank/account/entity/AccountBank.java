package com.bank.account.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
