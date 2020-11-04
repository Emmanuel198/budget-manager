package com.alkemy.budgetmanager.repository;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.models.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends PagingAndSortingRepository<OperationEntity, Long> {

    @Query("from OperationEntity")
    List<OperationEntity> findAllNotPaginated();

    Page<OperationEntity> findAllByType(OperationType operationType, Pageable pageable);
}
