package com.bank.account.service;

import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.entity.Client;

public class ClientDataTest {
    static ClientResquestDTO buildDefaultClient() {
        return ClientResquestDTO.builder()
                .mail("ducloux.y@gmail.com")
                .prenom("Yann")
                .nom("Ducloux")
                .password("bjklhdfbjlnkklbgdnf")
                .build();
    }
    static Client buildDefaultClientSaved() {
        return Client.builder()
                .mail("ducloux.y@gmail.com")
                .prenom("Yann")
                .nom("Ducloux")
                .password("$2a$10$Jp7JtCY1S8OR.Ah9CiAMHeG6grzVD7sC4CsNWBh1CWmZ3jRZoJwEi")
                .build();
    }
    static Client buildDefaultClientPAsswordFalseSaved() {
        return Client.builder()
                .mail("ducloux.y@gmail.com")
                .prenom("Yann")
                .nom("Ducloux")
                .password("$2a$10$uS4Im3WZ7PyyTg4lg/JrcebJ1QwSATUZihHq32T/beGg2raRbDaiG")
                .build();
    }
    static ClientResquestDTO buildEmptyClient() {
        return ClientResquestDTO.builder()
                .mail("ducloux.y@gmail.com")
                .prenom("Yann")
                .password("bjklhdfbjlnkklbgdnf")
                .build();
    }
    static ClientResquestDTO buildPasswordShortClient() {
        return ClientResquestDTO.builder()
                .mail("ducloux.y@gmail.com")
                .prenom("Yannn")
                .nom("Ducloux")
                .password("b")
                .build();
    }
}
