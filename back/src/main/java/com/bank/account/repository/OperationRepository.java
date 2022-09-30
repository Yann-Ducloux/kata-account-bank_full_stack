package com.bank.account.repository;

import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    @Query(value = "SELECT o FROM Operation o WHERE o.accountBank.id  = :accountBankId  ORDER BY o.dateOperation ")
    List<Operation> findByAccountBankId(Long accountBankId);
}