package com.bank.account.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConnectionRequestDTO {
    @Parameter(name="mail", description = "l'adresse mail", example = "ducloux.y@gmail.com")
    private String mail;
    @Parameter(name="password", description = "le mot de passe", example = "Caput Draconis")
    private String password;
}
