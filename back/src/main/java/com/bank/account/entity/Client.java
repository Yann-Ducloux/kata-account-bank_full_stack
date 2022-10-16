package com.bank.account.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Client {

    private @Id @GeneratedValue
    Long id;
    String mail;
    private String nom;
    private String prenom;
    private String password;
}