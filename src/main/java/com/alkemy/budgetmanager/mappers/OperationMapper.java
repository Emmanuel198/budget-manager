package com.alkemy.budgetmanager.mappers;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.models.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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


    public Operation map(OperationEntity operationEntity) {
        return modelMapper.map(operationEntity, Operation.class);
    }
    public List<Operation> map(List<OperationEntity> operationEntities) {
        List<Operation> operations = new ArrayList<>();
        for (OperationEntity operationEntity : operationEntities) {
            operations.add(map(operationEntity));
        }
        return operations;
    }
}
