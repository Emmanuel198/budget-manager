package com.alkemy.budgetmanager.service;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.exceptions.OperationNotFound;
import com.alkemy.budgetmanager.interfaceService.IOperationService;
import com.alkemy.budgetmanager.mappers.OperationMapper;
import com.alkemy.budgetmanager.models.Operation;
import com.alkemy.budgetmanager.models.OperationType;
import com.alkemy.budgetmanager.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OperationService implements IOperationService {

    private OperationRepository operationRepository;
    private OperationMapper operationMapper;

    @Autowired
    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    public Long createOperation(Operation operation) {
        LocalDate date = LocalDate.now();
        operation.setDate(date);
        OperationEntity createOperationEntity = operationRepository.save(operationMapper.map(operation));
        return createOperationEntity.getId();
    }

    public List<Operation> getAllOperations() {
        return operationMapper.map((List<OperationEntity>) operationRepository.findAll());
    }

    @Override
    public Page<OperationEntity> getAllOperations(Pageable pageable) {
        return operationRepository.findAll(pageable);
    }

    public Operation getOperationById(Long id) throws OperationNotFound {
        Optional<OperationEntity> operation = operationRepository.findById(id);
        if (operation.isEmpty()) {
            throw new OperationNotFound();
        }
        return operationMapper.map(operation.get());
    }

    public void deleteOperationById(Long id) throws OperationNotFound {
        Optional<OperationEntity> operation = operationRepository.findById(id);
        if (operation.isEmpty()) {
            throw new OperationNotFound();
        }
        operationRepository.deleteById(id);
    }

    public Double balanceOperation() {
        double totalAmount = 0;

        Iterable<OperationEntity> operations = operationRepository.findAll();

        for (OperationEntity operationEntity : operations) {
            if (operationEntity.getType().equals(OperationType.INCOME)) {
                totalAmount = totalAmount + operationEntity.getAmount();
            } else {
                totalAmount = totalAmount - operationEntity.getAmount();
            }
        }

        return totalAmount;
    }
}
