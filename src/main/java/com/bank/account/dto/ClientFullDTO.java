package com.bank.account.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientFullDTO {
    @Parameter(name="mail", description = "l'adresse mail", example = "ducloux.y@gmail.com")
    private String mail;
    private String nom;
    private String prenom;
    private String password;
}
