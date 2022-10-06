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
public class ClientDTO {
    @Parameter(name="mail", description = "l'adresse mail", example = "ducloux.y@gmail.com")
    private String mail;
    @Parameter(name="nom", description = "le nom", example = "Ducloux")
    private String nom;
    @Parameter(name="prenom", description = "le prénom", example = "Yann")
    private String prenom;
}
