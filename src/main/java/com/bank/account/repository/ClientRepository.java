package com.bank.account.repository;
import com.bank.account.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Boolean existsByMail(String mail);
    Optional<Client> findByMail(String mail);

    @Query("select case when count(c)> 0 then true else false end from Client c where c.mail=:mail and c.password=:password")
    Boolean passwordIsValid(String mail, String password);

}