package com.bank.account.service;

import com.bank.account.dto.AccountBankDTO;
import com.bank.account.dto.AccountBankFullDTO;
import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Client;
import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountBankService {
    private AccountBankRepository accountBankRepository;
    private ClientRepository clientRepository;
    ModelMapper modelMapper = new ModelMapper();
    public AccountBankService(AccountBankRepository accountBankRepository, ClientRepository clientRepository) {
        this.accountBankRepository = accountBankRepository;
        this.clientRepository = clientRepository;
    }

    public AccountBankFullDTO saveAccountBank(AccountBankDTO accountBankDTO) {
        List<Client> clientList = clientRepository.findByMail(accountBankDTO.getMail());
        AccountBank accountBank = new AccountBank();
        accountBank.setClient(clientList.get(0));
        accountBank.setDecouvert(accountBankDTO.getDecouvert());
        accountBank.setDateCreation(LocalDateTime.now());
        accountBank.setSolde(accountBankDTO.getSolde());
        return modelMapper.map(accountBankRepository.save(accountBank), AccountBankFullDTO.class);
    }


    public List<AccountBankFullDTO> getAllAccountBank(String mail) {
        List<Client> clientList = clientRepository.findByMail(mail);
        List<AccountBank> accountBanks = accountBankRepository.findByMail(mail);
        return accountBanks.stream()
                .map(accountBank -> modelMapper.map(accountBank, AccountBankFullDTO.class))
                .collect(Collectors.toList());
    }
}
