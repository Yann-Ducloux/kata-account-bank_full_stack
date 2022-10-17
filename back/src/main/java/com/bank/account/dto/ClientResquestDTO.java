package com.bank.account.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ClientResquestDTO {
    @Parameter(name="mail", description = "l'adresse mail", example = "ducloux.y@gmail.com")
    private String mail;
    @Parameter(name="nom", description = "le nom", example = "Ducloux")
    private String nom;
    @Parameter(name="prenom", description = "le prénom", example = "Yann")
    private String prenom;
    @Parameter(name="password", description = "le mot de passe", example = "Caput Draconis")
    private String password;
}
