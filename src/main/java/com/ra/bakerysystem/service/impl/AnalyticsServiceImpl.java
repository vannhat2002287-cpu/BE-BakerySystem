// 名前: Tram, Thuy
package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.model.DTO.DashboardResponseDTO;
import com.ra.bakerysystem.model.DTO.PopularProductDTO;
import com.ra.bakerysystem.repository.InventoryRepository;
import com.ra.bakerysystem.repository.OrderItemRepository;
import com.ra.bakerysystem.repository.OrderRepository;
import com.ra.bakerysystem.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public DashboardResponseDTO getDashboard() {

        // Xác định khoảng thời gian: từ đầu ngày đến cuối ngày hôm nay
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        // Tổng doanh thu trong ngày
        Integer dailySales = orderRepository.getDailySales(start, end);
        // Tổng số đơn hàng trong ngày
        Long orderCount = orderRepository.getOrderCount(start, end);
        // Số lượng sản phẩm đang ở mức tồn kho thấp
        Long lowStockCount = inventoryRepository.countLowStock();

        // Doanh thu theo từng giờ trong ngày
        List<Integer> hourlySales = getHourlySalesToday();

        // Lấy top 5 sản phẩm bán chạy nhất trong ngày
        List<PopularProductDTO> popularProducts =
                orderItemRepository.findTopProducts(PageRequest.of(0, 5))
                        .stream()
                        .map(row -> new PopularProductDTO(
                                ((Number) row[0]).longValue(),
                                (String) row[1],
                                ((Number) row[2]).longValue()
                        ))
                        .toList();

        // Build và trả về DashboardResponseDTO
        return DashboardResponseDTO.builder()
                .dailySales(dailySales)
                .orderCount(orderCount.intValue())
                .lowStockCount(lowStockCount.intValue())
                .hourlySales(hourlySales)
                .popularProducts(popularProducts)
                .build();
    }


    private List<Integer> getHourlySalesToday() {
        // Xác định ngày hiện tại
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(23, 59, 59);

        // Lấy dữ liệu raw từ database (hour, totalSales)
        List<Object[]> rawData = orderRepository.getHourlySales(start, end);

        // Khởi tạo mảng 24 giờ = 0
        List<Integer> hourlySales = new ArrayList<>(Collections.nCopies(24, 0));

        // Map dữ liệu từ query vào danh sách theo giờ
        for (Object[] row : rawData) {
            Integer hour = ((Number) row[0]).intValue();
            Integer total = ((Number) row[1]).intValue();
            hourlySales.set(hour, total);
        }

        return hourlySales;
    }

}

