package com.alkemy.budgetmanager.controller;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.exceptions.OperationNotFound;
import com.alkemy.budgetmanager.models.Operation;
import com.alkemy.budgetmanager.models.OperationType;
import com.alkemy.budgetmanager.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Controller
@RequestMapping("/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;



    @GetMapping
    public String index(@RequestParam Map<String, Object> params, Model model) {

        int page = (int) (params.get("page") != null ? (Long.parseLong(params.get("page").toString()) - 1) : 0);
        PageRequest pageRequest = PageRequest.of(page, 7);

        Page<OperationEntity> operationPage = operationService.getAllOperations(pageRequest);

        int totalPage = operationPage.getTotalPages();

        if (totalPage > 0) {

            List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        List<OperationEntity> operationList = operationPage.getContent();

        model.addAttribute("operations", operationList);
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("previous", page);
        model.addAttribute("last", totalPage);

        return "operation/operation-index";
    }
    @GetMapping("/save/{id}")
    public String showSave(@PathVariable("id") Long id, Model model) throws OperationNotFound {
        if (id != null && id != 0) {
            model.addAttribute("operation", operationService.getOperationById(id));
        } else {
            model.addAttribute("operation", new Operation());
        }
        model.addAttribute("operationType", OperationType.values());
        return "operation/operation-save";
    }
    
    @PostMapping("/save")
    public String createOperation(@Valid Operation operation, BindingResult bindingResult, Model model)
            throws OperationNotFound{

        if (bindingResult.hasErrors()) {
            model.addAttribute("operationType", OperationType.values());
            return "operation/operation-save";
        }
        operationService.createOperation(operation);
        return "redirect:/operations";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteOperationById(@PathVariable("id") Long id) throws OperationNotFound {
        operationService.deleteOperationById(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/find-by-id/{id}")
    public String getOperationById(@PathVariable Long id, Model model) throws OperationNotFound {
        model.addAttribute("operation", operationService.getOperationById(id));
        return "operation/operation-by-id";
    }


}
