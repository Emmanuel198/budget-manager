package com.alkemy.budgetmanager.repository;

import com.alkemy.budgetmanager.entity.OperationEntity;
import com.alkemy.budgetmanager.models.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OperationRepository extends PagingAndSortingRepository<OperationEntity, Long> {

    Page<OperationEntity> findAllByType(OperationType operationType, Pageable pageable);
}
