package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.repository.DailySalesSummaryRepository;
import com.ra.bakerysystem.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
/**
 * Service tính toán số lượng cần sản xuất cho từng sản phẩm trong ngày.
 *
 * Mục tiêu:
 * - Dựa trên dữ liệu bán hàng lịch sử (average daily sales)
 * - So sánh với số lượng đã bán trong ngày hiện tại
 * - Đề xuất số lượng cần sản xuất thêm cho factory
 */
@Service
public class ProductionCalculationService {
    // Repository tổng hợp doanh số theo ngày (analytics)
    private final DailySalesSummaryRepository dailyRepo;
    // Repository chi tiết từng item bán ra trong ngày
    private final OrderItemRepository orderItemRepo;
    /**
     * Constructor injection để đảm bảo service luôn có đủ dependency
     */
    public ProductionCalculationService(
            DailySalesSummaryRepository dailyRepo,
            OrderItemRepository orderItemRepo
    ) {
        this.dailyRepo = dailyRepo;
        this.orderItemRepo = orderItemRepo;
    }
    /**
     * Tính số lượng cần sản xuất cho một sản phẩm trong ngày.
     *
     * @param productId ID sản phẩm
     * @param today     Ngày hiện tại (business date)
     * @return số lượng cần sản xuất thêm
     */
    public int calculateProductionQuantity(Long productId, LocalDate today) {
// Doanh số trung bình mỗi ngày của sản phẩm (dựa trên lịch sử)
        double avg = dailyRepo.calculateAverageDailySales(String.valueOf(productId));
// Tổng số lượng đã bán trong ngày hôm nay
        int soldToday = orderItemRepo.sumSoldQuantityByProductAndDate(
                String.valueOf(productId), today
        );
// Số lượng cần sản xuất = average - đã bán hôm nay
        int qty = (int) Math.ceil(avg - soldToday);
// Luôn đảm bảo sản xuất tối thiểu 10 sản phẩm
        return Math.max(qty, 10);
    }
}
