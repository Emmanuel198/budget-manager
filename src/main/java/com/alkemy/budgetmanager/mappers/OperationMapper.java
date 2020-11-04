package com.alkemy.budgetmanager.mappers;

import com.alkemy.budgetmanager.controller.request.OperationRequest;
import com.alkemy.budgetmanager.controller.request.OperationUpdateRequest;
import com.alkemy.budgetmanager.controller.response.BalanceResponse;
import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.models.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OperationMapper {

    private ModelMapper modelMapper;
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    public OperationMapper(ModelMapper modelMapper, DateTimeFormatter dateTimeFormatter) {
        this.modelMapper = modelMapper;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public OperationEntity map(Operation operation) {
        return modelMapper.map(operation, OperationEntity.class);
    }

    public BalanceResponse map(Double balance) {
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setBalance(balance);
        return balanceResponse;
    }

    public Operation map(OperationUpdateRequest operationUpdateRequest) {
        Operation operation = new Operation();
        operation.setConcept(operationUpdateRequest.getConcept());
        operation.setAmount(operationUpdateRequest.getAmount());
        operation.setDate(LocalDateTime.parse(operationUpdateRequest.getDate(), dateTimeFormatter));
        return operation;
    }

    public Operation map(OperationRequest operationRequest) {
        Operation operation = new Operation();
        operation.setConcept(operationRequest.getConcept());
        operation.setAmount(operationRequest.getAmount());
        operation.setDate(LocalDateTime.parse(operationRequest.getDate(), dateTimeFormatter));
        operation.setType(operationRequest.getType());
        return operation;
    }

    public Operation map(OperationEntity operationEntity) {
        return modelMapper.map(operationEntity, Operation.class);
    }
}
