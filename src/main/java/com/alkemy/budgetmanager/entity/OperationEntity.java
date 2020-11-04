package com.alkemy.budgetmanager.entity;

import com.alkemy.budgetmanager.models.OperationType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation")
public class OperationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String concept;
    @Column
    private Double amount;
    @Column
    private LocalDateTime date;
    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private OperationType type;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}
