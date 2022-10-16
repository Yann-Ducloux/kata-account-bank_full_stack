package com.bank.account.service;

import com.bank.account.dto.ClientResquestDTO;
import com.bank.account.dto.ConnectionRequestDTO;

public class ConnectionDataTest {
    static ConnectionRequestDTO buildDefaultConnection() {
        return ConnectionRequestDTO.builder()
                .mail("ducloux.y@gmail.com")
                .password("bjklhdfbjlnkklbgdnf")
                .build();
    }
    static ConnectionRequestDTO buildEmptyConnection() {
        return ConnectionRequestDTO.builder()
                .mail("ducloux.y@gmail.com")
                .build();
    }
}
