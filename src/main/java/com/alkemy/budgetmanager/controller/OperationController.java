package com.alkemy.budgetmanager.controller;

import com.alkemy.budgetmanager.controller.request.OperationRequest;
import com.alkemy.budgetmanager.controller.request.OperationUpdateRequest;
import com.alkemy.budgetmanager.controller.response.BalanceResponse;
import com.alkemy.budgetmanager.controller.response.OperationResponse;
import com.alkemy.budgetmanager.exceptions.OperationNotFound;
import com.alkemy.budgetmanager.mappers.OperationMapper;
import com.alkemy.budgetmanager.models.OperationType;
import com.alkemy.budgetmanager.models.Paginated;
import com.alkemy.budgetmanager.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;
    @Autowired
    private OperationMapper operationMapper;
    @Autowired
    private HttpServletRequest request;

    @PostMapping
    public ResponseEntity<OperationResponse> createOperation(@RequestBody OperationRequest operationRequest) {
        Long operationId = operationService.createOperation(operationMapper.map(operationRequest));
        return ResponseEntity.created(URI.create(request.getRequestURL().toString() + "/" + operationId)).build();
    }

    @GetMapping
    public ResponseEntity<Paginated> getAllOperations(@RequestParam(required = false) Integer page,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) OperationType type) {
        return ResponseEntity.ok(operationService.getAllOperations(page, pageSize, type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperationById(@PathVariable("id") Long id) throws OperationNotFound {
        operationService.deleteOperationById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/operation/{id}")
    public ResponseEntity<OperationResponse> updateOperation
            (@PathVariable Long id, @RequestBody OperationUpdateRequest operationUpdateRequest) throws OperationNotFound {
        operationService.updateOperation(operationMapper.map(operationUpdateRequest), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponse> balanceOperation() {
        return ResponseEntity.ok(operationMapper.map(operationService.balanceOperation()));
    }
}
