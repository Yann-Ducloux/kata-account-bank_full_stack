package com.bank.account.service;

import com.bank.account.dto.AccountBankRequestDTO;
import com.bank.account.dto.AccountBankResponseDTO;
import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Client;
import com.bank.account.exception.*;
import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    /**
     * Cette fonction créer un compte banquaire
     * @param accountBankRequestDTO les info du compte en bank que l'on va créer
     * @param mail le mail
     * @return AccountBankFullDTO les infos du compte créer
     * @throws MailNotFillException
     * @throws SoldeException
     * @throws DecouvertException
     * @throws ClientMailExistException
     */
    public AccountBankResponseDTO saveAccountBank(AccountBankRequestDTO accountBankRequestDTO, String mail) {
        controleMail(mail);
        controleSaveAccountBank(accountBankRequestDTO);
        Optional<Client> clientOptional = clientRepository.findByMail(mail);
        if(!clientOptional.isPresent()) {
            throw new ClientMailExistException(mail);
        }
        AccountBank accountBank = new AccountBank();
        accountBank.setClient(clientOptional.get());
        accountBank.setDecouvert(accountBankRequestDTO.getDecouvert());
        accountBank.setDateCreation(LocalDateTime.now());
        accountBank.setSolde(accountBankRequestDTO.getSolde());
        AccountBank accountBankSaved = accountBankRepository.save(accountBank);
        return modelMapper.map(accountBankSaved, AccountBankResponseDTO.class);
    }

    private void controleSaveAccountBank(AccountBankRequestDTO accountBankRequestDTO) {
        if(accountBankRequestDTO.getSolde() == null || accountBankRequestDTO.getSolde()<0) {
            throw new SoldeException();
        }
        if(accountBankRequestDTO.getDecouvert() == null || accountBankRequestDTO.getDecouvert()<0) {
            throw new DecouvertException();
        }
    }

    private void controleMail(String mail) {
        if(mail ==null || mail.isEmpty()) {
            throw new MailNotFillException();
        }
    }

    /**
     * Cette fonction récupérer la liste des compte
     * @param mail le mail
     * @return  List<AccountBankFullDTO> la liste de comptes rattachez au mail
     * @throws MailNotFillException
     */
    public List<AccountBankResponseDTO> getAllAccountBank(String mail) {
        controleMail(mail);
        List<AccountBank> accountBanks = accountBankRepository.findByMail(mail);
        return accountBanks.stream()
                .map(accountBank -> modelMapper.map(accountBank, AccountBankResponseDTO.class))
                .collect(Collectors.toList());
    }
}
