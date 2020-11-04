package com.alkemy.budgetmanager.service;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.exceptions.OperationNotFound;
import com.alkemy.budgetmanager.mappers.OperationMapper;
import com.alkemy.budgetmanager.models.Operation;
import com.alkemy.budgetmanager.models.OperationType;
import com.alkemy.budgetmanager.models.Paginated;
import com.alkemy.budgetmanager.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationService {

    private OperationRepository operationRepository;
    private OperationMapper operationMapper;

    @Autowired
    public OperationService(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    /**
     * Creates a new {@link Operation}
     *
     * @param operation
     * @return The id of the created {@link Operation}
     */
    public Long createOperation(Operation operation) {
        OperationEntity createdOperationEntity = operationRepository.save(operationMapper.map(operation));
        return createdOperationEntity.getId();
    }

    /**
     * Get all {@link Operation} paginated and filtered
     *
     * @param page
     * @param pageSize
     * @param operationType
     * @return {@link Paginated} response of the filtered {@link Operation}
     */
    public Paginated getAllOperations(Integer page, Integer pageSize, OperationType operationType) {
        if (pageSize == null) {
            pageSize = 10;
        }
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("date").descending());

        Page<OperationEntity> pageResponse;
        if (operationType != null) {
            pageResponse = operationRepository.findAllByType(operationType, pageable);
        } else {
            pageResponse = operationRepository.findAll(pageable);
        }

        Paginated<OperationEntity> paginated = new Paginated<>();
        paginated.setPage(page);
        paginated.setPageSize(pageSize);
        paginated.setTotalPages(pageResponse.getTotalPages());
        paginated.setTotalElements(pageResponse.getTotalElements());
        paginated.setElements(pageResponse.getContent());
        return paginated;
    }

    /**
     * Get an {@link Operation} by id
     *
     * @param id of the {@link Operation}
     * @return {@link Operation}
     * @throws OperationNotFound
     */
    public Operation getOperationById(Long id) throws OperationNotFound {
        Optional<OperationEntity> operation = operationRepository.findById(id);
        if (operation.isEmpty()) {
            throw new OperationNotFound();
        }
        return operationMapper.map(operation.get());
    }

    /**
     * Deletes an {@link Operation} by id
     *
     * @param id of the operation
     * @throws OperationNotFound
     */
    public void deleteOperationById(Long id) throws OperationNotFound {
        Optional<OperationEntity> operation = operationRepository.findById(id);
        if (operation.isEmpty()) {
            throw new OperationNotFound();
        }
        operationRepository.deleteById(id);
    }

    /**
     * Updates an {@link Operation}
     *
     * @param operation with new properties
     * @param id        of the preexisting operation
     * @throws OperationNotFound
     */
    public void updateOperation(Operation operation, Long id) throws OperationNotFound {
        OperationEntity operationEntity = operationRepository.findById(id)
                .orElseThrow(OperationNotFound::new);
        if (operation.getAmount() != null) {
            operationEntity.setAmount(operation.getAmount());
        }
        if (operation.getConcept() != null) {
            operationEntity.setConcept(operation.getConcept());
        }
        if (operation.getDate() != null) {
            operationEntity.setDate(operation.getDate());
        }
        operationRepository.save(operationEntity);
    }

    /**
     * Calculates the actual account balance resulting from adding the INCOMES and subtracting the EXPENSES
     *
     * @return balance
     */
    public Double balanceOperation() {
        double totalAmount = 0;

        List<OperationEntity> operations = operationRepository.findAllNotPaginated();

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
