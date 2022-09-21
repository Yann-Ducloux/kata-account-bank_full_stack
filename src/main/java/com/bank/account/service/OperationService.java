package com.bank.account.service;

import com.bank.account.dto.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
            throw new AccountBankNotExistException(operationDTO.getIdAccountBank());
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

    public List<HistoriqueOperationDTO> getHistorique(String mail) {
        List<AccountBank> accountBanks = accountBankRepository.findByMail(mail);
        List<HistoriqueOperationDTO>  historiqueOperationDTOS;
        historiqueOperationDTOS = accountBanks.stream().map(accountBank-> {
            HistoriqueOperationDTO historiqueOperationDTO = new HistoriqueOperationDTO();
            historiqueOperationDTO = modelMapper.map(accountBank, HistoriqueOperationDTO.class);
            historiqueOperationDTO.setAccountBankid(accountBank.getId());
            List<Operation>  operations = this.operationRepository.findByAccountBankId(accountBank.getId());
            historiqueOperationDTO.setOperationLightDTO(
                    operations.stream()
                    .map(operation -> modelMapper.map(operation, OperationLightDTO.class))
                    .collect(Collectors.toList())
            );
            return historiqueOperationDTO;
        }).collect(toList());
        return historiqueOperationDTOS;
    }
}
