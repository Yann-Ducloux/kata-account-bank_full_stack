package com.bank.account;


import com.bank.account.entity.Client;
import com.bank.account.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientRepository repository) {
        Client clientTest = new Client();
        clientTest.setMail("test@gmail.com");
        clientTest.setNom("Ducloux");
        clientTest.setPrenom("Yann");
        clientTest.setPassword("*******");
        return args -> {
            log.info("Preloading " + repository.save(clientTest));
        };
    }
}
