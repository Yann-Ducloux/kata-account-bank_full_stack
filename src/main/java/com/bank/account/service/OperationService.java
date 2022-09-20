package com.bank.account.service;

import com.bank.account.dto.AccountBankDTO;
import com.bank.account.dto.AccountBankFullDTO;
import com.bank.account.dto.OperationDTO;
import com.bank.account.dto.RecuDTO;
import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Client;
import com.bank.account.entity.Operation;
import com.bank.account.enumeration.TypeOperation;
import com.bank.account.exception.*;
import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.ClientRepository;
import com.bank.account.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService {
    private OperationRepository operationRepository;
    private AccountBankRepository accountBankRepository;
    ModelMapper modelMapper = new ModelMapper();
    public OperationService(OperationRepository operationRepository, AccountBankRepository accountBankRepository) {
        this.operationRepository = operationRepository;
        this.accountBankRepository = accountBankRepository;
    }

    public RecuDTO saveOperation(OperationDTO operationDTO) {
        if(operationDTO==null || operationDTO.getTypeOperation() ==null || operationDTO.getIdAccountBank() ==null|| operationDTO.getSomme() ==null ) {
            throw new OperationDonneManquanteExcepion();
        }
        if(operationDTO.getSomme()<=0) {
            throw new OperationSommeNulException(operationDTO.getSomme());
        }
        TypeOperation typeOperation = operationDTO.getTypeOperation();
        Optional<AccountBank> accountBankOptional = accountBankRepository.findById(operationDTO.getIdAccountBank());
        if (!accountBankOptional.isPresent()) {
            return null;
        }

        AccountBank accountBank = accountBankOptional.get();
        Operation operation = new Operation();

        if (typeOperation.equals(TypeOperation.DEPOSIT)) {
            accountBank.setSolde(accountBank.getSolde() + operationDTO.getSomme());

        } else if(typeOperation.equals(TypeOperation.WITHDRAWAL)){
            if(accountBank.getDecouvert()<0) {
                throw new DecouvertException(accountBank.getDecouvert());
            }
            if(-accountBank.getDecouvert()>accountBank.getSolde() - operationDTO.getSomme()) {
                throw new DecouvertPlafondException(accountBank.getDecouvert());
            }
            accountBank.setSolde(accountBank.getSolde() - operationDTO.getSomme());
        } else {
            throw new TypeOperationNotExistException();
        }

        accountBank = accountBankRepository.save(accountBank);
        operation.setAccountBank(accountBank);
        operation.setTypeOperation(typeOperation);
        operation.setDateOperation(LocalDateTime.now());
        operation.setSomme(operationDTO.getSomme());
        this.operationRepository.save(operation);
        RecuDTO recu = new RecuDTO();
        recu.setDateOperation(operation.getDateOperation());
        recu.setTypeOperation(typeOperation);
        recu.setIdAccountBank(operationDTO.getIdAccountBank());
        recu.setSomme(operation.getSomme());
        return recu;
    }
}
