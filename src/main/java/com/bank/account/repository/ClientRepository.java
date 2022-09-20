package com.bank.account.repository;
import com.bank.account.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByMail(String mail);
    List<Client> findByMail(String mail);
}