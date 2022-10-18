package com.bank.account.service;

import com.bank.account.dto.*;
import com.bank.account.entity.AccountBank;
import com.bank.account.entity.Operation;
import com.bank.account.enumeration.TypeOperation;
import com.bank.account.exception.*;
import com.bank.account.repository.AccountBankRepository;
import com.bank.account.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
     * @param operationRequestDTO les infos de l'opération
     * @param mail le mail de l'utilisateur
     * @return RecuDTO reçu de l'opération
     * @throws MailNotFillException
     * @throws OperationDonneManquanteExcepion
     * @throws OperationSommeNulException
     * @throws TypeOperationNotExistException
     */
    public RecuResponseDTO saveOperation(OperationRequestDTO operationRequestDTO, String mail) throws MailNotFillException, OperationDonneManquanteExcepion, OperationSommeNulException, TypeOperationNotExistException {

        controleOperation(operationRequestDTO, mail);

        TypeOperation typeOperation = operationRequestDTO.getTypeOperation();
        Optional<AccountBank> accountBankOptional = accountBankRepository.findById(operationRequestDTO.getIdAccountBank());
        controleAccountBank(accountBankOptional, operationRequestDTO.getIdAccountBank());

        AccountBank accountBank = accountBankOptional.get();
        controleAccountBankMail(accountBank, mail);
        accountBank.setSolde(recupSolde(accountBank, operationRequestDTO));
        accountBank = accountBankRepository.save(accountBank);
        Operation operationNew = recupOperation(operationRequestDTO, accountBank);
        Operation operationSaved = this.operationRepository.save(operationNew);
        return recupRecu(operationSaved);
    }

    /**
     * fonction qui controle les info avaant de caaluler l'opération
     * @param operationRequestDTO les infos de l'opération
     * @param mail le mail de l'utilisateur
     * @throws MailNotFillException
     * @throws OperationDonneManquanteExcepion
     * @throws OperationSommeNulException
     */
    private void controleOperation(OperationRequestDTO operationRequestDTO, String mail) {
        if(mail ==null ||mail.isEmpty()) {
            throw new MailNotFillException();
        }
        if(operationRequestDTO ==null || operationRequestDTO.getTypeOperation() ==null || operationRequestDTO.getIdAccountBank() ==null|| operationRequestDTO.getSomme() ==null ) {
            throw new OperationDonneManquanteExcepion();
        }
        if(operationRequestDTO.getSomme()<=0) {
            throw new OperationSommeNulException(operationRequestDTO.getSomme());
        }
    }

    /**
     * fonction qui calcul le solde
     * @param accountBank les info du compte en bank
     * @param operationRequestDTO les infos de l'opération
     * @return solde la valeur du solde
     */
    private Long recupSolde(AccountBank accountBank, OperationRequestDTO operationRequestDTO){
        if (operationRequestDTO.getTypeOperation().equals(TypeOperation.DEPOSIT)) {
            if(accountBank.getSolde() ==null) {
                throw new SoldeException();
            }
            return accountBank.getSolde() + operationRequestDTO.getSomme();
        } else if(operationRequestDTO.getTypeOperation().equals(TypeOperation.WITHDRAWAL)){
            controleSolde(accountBank, operationRequestDTO);
            return accountBank.getSolde() - operationRequestDTO.getSomme();
        } else {
            throw new TypeOperationNotExistException();
        }
    }

    /**
     * fonction qui contrôle le solde
     * @param accountBank les info du compte en bank
     * @param operationRequestDTO les infos de l'opération
     * @throws DecouvertException
     * @throws SoldeException
     * @throws DecouvertException
     * @throws DecouvertPlafondException
     */
    private void controleSolde(AccountBank accountBank, OperationRequestDTO operationRequestDTO) {
        if(accountBank.getDecouvert() ==null || accountBank.getDecouvert()<0) {
            throw new DecouvertException();
        }
        if(accountBank.getSolde() ==null) {
            throw new SoldeException();
        }
        if(-accountBank.getDecouvert()>accountBank.getSolde() - operationRequestDTO.getSomme()) {
            throw new DecouvertPlafondException();
        }
    }

    /**
     * fonction qui vérifie que le mail du compte correspond a celui qui est identifié
     * @param accountBank les info du compte en bank
     * @param mail le mail de l'utilisateur
     * @throws ClientMailNotEqualsException
     */
    private void controleAccountBankMail(AccountBank accountBank, String mail) {
        if(accountBank.getClient() ==null || accountBank.getClient().getMail() ==null) {
            throw new OperationDonneManquanteExcepion();
        }
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
     * @param operationRequestDTO les infos de l'opération
     * @param accountBank les info du compte en bank
     * @return operation
     */
    private Operation recupOperation(OperationRequestDTO operationRequestDTO, AccountBank accountBank) {
        Operation operation = modelMapper.map(operationRequestDTO, Operation.class);
        operation.setDateOperation(LocalDateTime.now());
        operation.setAccountBank(accountBank);
        return operation;
    }

    /**
     * fonction qui transforme le reçu
     * @param operation les infos de l'opération
     * @return Recu
     */
    private RecuResponseDTO recupRecu(Operation operation) {
        RecuResponseDTO recu = modelMapper.map(operation, RecuResponseDTO.class);
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
