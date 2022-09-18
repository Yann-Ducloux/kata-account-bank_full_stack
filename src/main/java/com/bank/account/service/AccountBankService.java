package com.bank.account.service;

import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AccountBankService {
    private AccountBankRepository accountBankRepository;
    ModelMapper modelMapper = new ModelMapper();
    public AccountBankService(AccountBankRepository accountBankRepository) {
        this.accountBankRepository = accountBankRepository;
    }
}
