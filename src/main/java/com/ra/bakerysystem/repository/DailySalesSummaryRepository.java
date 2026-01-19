package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.DailySalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DailySalesSummaryRepository
        extends JpaRepository<DailySalesSummary, Long> {

    @Query("""
    SELECT COALESCE(AVG(d.totalSold), 0)
    FROM DailySalesSummary d
    WHERE d.productId = :productId
""")
    Double calculateAverageDailySales(@Param("productId") String productId);
}