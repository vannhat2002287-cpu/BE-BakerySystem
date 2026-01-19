package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.repository.DailySalesSummaryRepository;
import com.ra.bakerysystem.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductionCalculationService {

    private final DailySalesSummaryRepository dailyRepo;
    private final OrderItemRepository orderItemRepo;

    public ProductionCalculationService(
            DailySalesSummaryRepository dailyRepo,
            OrderItemRepository orderItemRepo
    ) {
        this.dailyRepo = dailyRepo;
        this.orderItemRepo = orderItemRepo;
    }

    public int calculateProductionQuantity(Long productId, LocalDate today) {

        double avg = dailyRepo.calculateAverageDailySales(String.valueOf(productId));

        int soldToday = orderItemRepo.sumSoldQuantityByProductAndDate(
                String.valueOf(productId), today
        );

        int qty = (int) Math.ceil(avg - soldToday);

        return Math.max(qty, 10);
    }
}
