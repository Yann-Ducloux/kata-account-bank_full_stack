package com.bank.account.service;

import com.bank.account.repository.ClientRepository;
import com.bank.account.repository.OperationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
    private OperationRepository operationRepository;
    ModelMapper modelMapper = new ModelMapper();
    public OperationService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }
}
