package com.alkemy.budgetmanager.controller.request;

import com.alkemy.budgetmanager.models.OperationType;

public class OperationRequest {

    private String concept;
    private Double amount;
    private String date;
    private OperationType type;

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}
