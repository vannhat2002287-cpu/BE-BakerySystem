package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import com.ra.bakerysystem.model.entity.FactoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FactoryRequestRepository extends JpaRepository<FactoryRequest, Long> {
    boolean existsByProductIdAndBusinessDateAndStatusIn(
            Long productId,
            LocalDate businessDate,
            List<FactoryRequestStatus> statuses
    );
}

