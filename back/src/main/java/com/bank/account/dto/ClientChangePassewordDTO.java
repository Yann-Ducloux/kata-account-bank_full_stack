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
public class ClientChangePassewordDTO {
    @Parameter(name="mail", description = "l'adresse mail", example = "ducloux.y@gmail.com")
    private String mail;
    @Parameter(name="nom", description = "le nom", example = "Ducloux")
    private String nom;
    @Parameter(name="prenom", description = "le prénom", example = "Yann")
    private String prenom;
    @Parameter(name="password", description = "le nouveau mot de passe", example = "Caput Draconis")
    private String password;
    @Parameter(name="oldpassword", description = "l'ancien mot de passe", example = "Alea jacta est")
    private String oldPassword;
}
