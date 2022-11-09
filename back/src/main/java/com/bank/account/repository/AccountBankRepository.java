package com.bank.account.repository;

import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountBankRepository extends JpaRepository<AccountBank, Long> {
    @Query(value = "SELECT a FROM AccountBank a  WHERE a.client.mail = :mail")
    List<AccountBank> findByMail(String mail);
    @Query(value = "SELECT a FROM AccountBank a  WHERE a.client.mail = :mail AND a.id = :accountBankId")
    Optional<AccountBank> findByMailAndAccountBankId(String mail, Long accountBankId);

}