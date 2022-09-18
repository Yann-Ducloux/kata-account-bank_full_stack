package com.bank.account.repository;

import com.bank.account.entity.AccountBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBankRepository extends JpaRepository<AccountBank, Long> {

}