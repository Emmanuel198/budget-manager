package com.alkemy.budgetmanager.service;

import com.alkemy.budgetmanager.config.ApiConfiguration;
import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.exceptions.OperationNotFound;
import com.alkemy.budgetmanager.mappers.OperationMapper;
import com.alkemy.budgetmanager.models.Operation;
import com.alkemy.budgetmanager.repository.OperationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class OperationServiceTest {
    @Mock
    OperationRepository operationRepository;
    OperationMapper operationMapper;
    OperationService operationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ApiConfiguration apiConfiguration = new ApiConfiguration();
        operationMapper = new OperationMapper(apiConfiguration.modelMapper(), apiConfiguration.dateTimeFormatter());
        operationService = new OperationService(operationRepository, operationMapper);
    }

    @Test
    public void testCreateOperation() throws Exception {
        Long id = 1L;

        Operation operation = new Operation();
        operation.setId(id);

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(id);

        when(operationRepository.save(any())).thenReturn(operationEntity);

        Long resultId = operationService.createOperation(operation);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void testGetAllOperations() throws Exception {

    }

    @Test
    public void testGetOperationById() throws Exception {
        Long id = 1L;
        Double amount = 200.00;
        String concept = "Test";

        Operation operation = new Operation();
        operation.setId(id);
        operation.setAmount(amount);
        operation.setConcept(concept);

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(id);
        operationEntity.setConcept(concept);
        operationEntity.setAmount(amount);

        when(operationRepository.findById(id)).thenReturn(java.util.Optional.of(operationEntity));

        Operation operationResult = operationService.getOperationById(id);

        Assertions.assertEquals(id, operationResult.getId());
        Assertions.assertEquals(amount, operationResult.getAmount());
        Assertions.assertEquals(concept, operationResult.getConcept());
    }

    @Test
    public void testDeleteOperationById() throws Exception {
        when(operationRepository.findById(1L)).thenReturn(java.util.Optional.of(new OperationEntity()));

        operationService.deleteOperationById(1L);

        verify(operationRepository, times(1)).findById(1L);
        verify(operationRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testUpdateOperation() throws Exception {

    }

    @Test
    public void testGetOperationByIdException() throws Exception {
        Long id = 1L;

        when(operationRepository.findById(id)).thenReturn(Optional.empty());

        try {
            operationService.getOperationById(id);
        } catch (OperationNotFound e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testDeleteByIdException() throws Exception {
        Long id = 1L;

        when(operationRepository.findById(id)).thenReturn(Optional.empty());

        try {
            operationService.deleteOperationById(id);
        } catch (OperationNotFound e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testBalanceOperation() {

    }
}

