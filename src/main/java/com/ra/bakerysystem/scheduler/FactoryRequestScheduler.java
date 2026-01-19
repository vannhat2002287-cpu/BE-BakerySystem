package com.ra.bakerysystem.scheduler;

import com.ra.bakerysystem.service.FactoryRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * Scheduler dùng để TỰ ĐỘNG tạo Factory Request theo giờ cố định
 *
 *Mục đích:
 * - Tự động gửi yêu cầu sản xuất cho factory
 * - Dựa trên tồn kho hiện tại + logic tính toán sản xuất
 *
 *Thời điểm chạy:
 * - 12:00 trưa
 * - 17:00 chiều
 *
 *Flow tổng:
 * Scheduler
 *   → FactoryRequestService.autoCreateFactoryRequests()
 *   → tính số lượng cần sản xuất
 *   → tạo bản ghi trong bảng factory_requests
 */

@Component
@EnableScheduling
@RequiredArgsConstructor
public class FactoryRequestScheduler {
//Service xử lý nghiệp vụ Factory Request
    private final FactoryRequestService factoryRequestService;
    /**
     * Job chạy tự động theo cron
     *
     * Cron: "0 0 12,17 * * *"
     * → chạy lúc 12:00 và 17:00 mỗi ngày
     *
     * Không có request từ client
     * Chạy hoàn toàn tự động trên server
     */
    @Scheduled(cron = "0 0 12,17 * * *")
    public void runAutoFactoryRequests() {
        factoryRequestService.autoCreateFactoryRequests();
    }
}