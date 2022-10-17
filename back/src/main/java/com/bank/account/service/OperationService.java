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

    /**
     * fonction qui savegarde l'opération
     * @param operationDTO les infos de l'opération
     * @param mail le mail de l'utilisateur
     * @return RecuDTO reçu de l'opération
     * @throws MailNotFillException
     * @throws OperationDonneManquanteExcepion
     * @throws OperationSommeNulException
     * @throws TypeOperationNotExistException
     */
    public RecuDTO saveOperation(OperationDTO operationDTO, String mail) throws MailNotFillException, OperationDonneManquanteExcepion, OperationSommeNulException, TypeOperationNotExistException {

        controleOperation(operationDTO, mail);

        TypeOperation typeOperation = operationDTO.getTypeOperation();
        Optional<AccountBank> accountBankOptional = accountBankRepository.findById(operationDTO.getIdAccountBank());
        controleAccountBank(accountBankOptional, operationDTO.getIdAccountBank());

        AccountBank accountBank = accountBankOptional.get();
        controleAccountBankMail(accountBank, mail);
        accountBank.setSolde(recupSolde(accountBank, operationDTO));
        accountBank = accountBankRepository.save(accountBank);

        return recupRecu(this.operationRepository.save(recupOperation(operationDTO, accountBank)));
    }

    /**
     * fonction qui controle les info avaant de caaluler l'opération
     * @param operationDTO les infos de l'opération
     * @param mail le mail de l'utilisateur
     * @throws MailNotFillException
     * @throws OperationDonneManquanteExcepion
     * @throws OperationSommeNulException
     */
    private void controleOperation(OperationDTO operationDTO, String mail) {
        if(mail ==null ||mail.isEmpty()) {
            throw new MailNotFillException();
        }
        if(operationDTO==null || operationDTO.getTypeOperation() ==null || operationDTO.getIdAccountBank() ==null|| operationDTO.getSomme() ==null ) {
            throw new OperationDonneManquanteExcepion();
        }
        if(operationDTO.getSomme()<=0) {
            throw new OperationSommeNulException(operationDTO.getSomme());
        }
    }

    /**
     * fonction qui calcul le solde
     * @param accountBank les info du compte en bank
     * @param operationDTO les infos de l'opération
     * @return solde la valeur du solde
     */
    private Long recupSolde(AccountBank accountBank, OperationDTO operationDTO){
        if (operationDTO.getTypeOperation().equals(TypeOperation.DEPOSIT)) {
            return accountBank.getSolde() + operationDTO.getSomme();
        } else if(operationDTO.getTypeOperation().equals(TypeOperation.WITHDRAWAL)){
            controleSolde(accountBank, operationDTO);
            return accountBank.getSolde() - operationDTO.getSomme();
        } else {
            throw new TypeOperationNotExistException();
        }
    }

    /**
     * fonction qui contrôle le solde
     * @param accountBank les info du compte en bank
     * @param operationDTO les infos de l'opération
     * @throws DecouvertException
     * @throws SoldeException
     * @throws DecouvertException
     * @throws DecouvertPlafondException
     */
    private void controleSolde(AccountBank accountBank, OperationDTO operationDTO) {
        if(accountBank.getDecouvert() ==null || accountBank.getDecouvert()<0) {
            throw new DecouvertException();
        }
        if(accountBank.getSolde() ==null) {
            throw new SoldeException();
        }
        if(operationDTO.getSomme() ==null || operationDTO.getSomme()<0) {
            throw new SommeErreurException();
        }
        if(-accountBank.getDecouvert()>accountBank.getSolde() - operationDTO.getSomme()) {
            throw new DecouvertPlafondException(accountBank.getDecouvert());
        }
    }

    /**
     * fonction qui vérifie que le mail du compte correspond a celui qui est identifié
     * @param accountBank les info du compte en bank
     * @param mail le mail de l'utilisateur
     * @throws ClientMailNotEqualsException
     */
    private void controleAccountBankMail(AccountBank accountBank, String mail) {
        if (!accountBank.getClient().getMail().equals(mail) ) {
            throw new ClientMailNotEqualsException(mail);
        }
    }

    /**
     * fonction qui transforme le reçu
     * @param accountBankOptional les info du compte en bank
     * @param idAccountBank  identifiant du compte en bank
     * @throws AccountBankNotExistException
     */
    private void controleAccountBank(Optional<AccountBank> accountBankOptional, Long idAccountBank) {
        if (!accountBankOptional.isPresent()) {
            throw new AccountBankNotExistException(idAccountBank);
        }
    }

    /**
     * fonction qui transforme le l'opération
     * @param operationDTO les infos de l'opération
     * @param accountBank les info du compte en bank
     * @return operation
     */
    private Operation recupOperation(OperationDTO operationDTO, AccountBank accountBank) {
        Operation operation = modelMapper.map(operationDTO, Operation.class);
        operation.setDateOperation(LocalDateTime.now());
        operation.setAccountBank(accountBank);
        return operation;
    }

    /**
     * fonction qui transforme le reçu
     * @param operation les infos de l'opération
     * @return Recu
     */
    private RecuDTO recupRecu(Operation operation) {
        RecuDTO recu = modelMapper.map(operation, RecuDTO.class);
        recu.setIdAccountBank(operation.getAccountBank().getId());
        return recu;
    }

    /**
     * fonction qui récupére l'historique d'une personne
     * @param mail le mail de l'utilisateur
     * @return List<HistoriqueOperationDTO> l'historiaqqque de toute les opérations
     * @throws MailNotFillException
     * @throws AccountBankHaveNotException
     */
    public List<HistoriqueOperationDTO> getHistorique(String mail) {
        if(mail ==null || mail.isEmpty()) {
            throw new MailNotFillException();
        }
        List<AccountBank> accountBanks = accountBankRepository.findByMail(mail);
        if(accountBanks.isEmpty()) {
            throw new AccountBankHaveNotException();
        }
        return recupHistorique(accountBanks);
    }

    /**
     * fonction qui récupére l'historique d'une personne
     * @param accountBanks les info du compte en bank
     * @return List<HistoriqueOperationDTO> l'historiaqqque de toute les opérations
     */
    private List<HistoriqueOperationDTO> recupHistorique(List<AccountBank> accountBanks) {
       return accountBanks.stream().map(accountBank-> {
            HistoriqueOperationDTO historiqueOperationDTO = modelMapper.map(accountBank, HistoriqueOperationDTO.class);
            historiqueOperationDTO.setAccountBankid(accountBank.getId());
            List<Operation>  operations = this.operationRepository.findByAccountBankId(accountBank.getId());
            historiqueOperationDTO.setOperationLightDTO(
                    operations.stream()
                            .map(operation -> modelMapper.map(operation, OperationLightDTO.class))
                            .collect(Collectors.toList())
            );
            return historiqueOperationDTO;
        }).collect(toList());
    }
}
