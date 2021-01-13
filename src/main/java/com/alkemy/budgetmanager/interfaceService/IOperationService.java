package com.alkemy.budgetmanager.interfaceService;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.exceptions.OperationNotFound;
import com.alkemy.budgetmanager.models.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOperationService {

    Long createOperation(Operation operation);

    List<Operation> getAllOperations();

    Operation getOperationById(Long id) throws OperationNotFound;

    void deleteOperationById(Long id) throws OperationNotFound;

    Page<OperationEntity> getAllOperations(Pageable pageable);

}
