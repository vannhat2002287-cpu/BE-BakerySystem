// 名前: Tram, Thuy
package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.DashboardResponseDTO;
import com.ra.bakerysystem.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics API")

/**
 * AnalyticsController quản lý các API liên quan đến thống kê và phân tích dữ liệu.
 * Giúp:
 *  - Hiển thị dashboard tổng quan
 *  - Cung cấp dữ liệu thống kê (doanh thu, đơn hàng, sản phẩm, v.v.)
 *  - Phục vụ biểu đồ (charts) trên giao diện quản trị
 */

public class AnalyticsController {

    /**
     * Service xử lý logic thống kê và tổng hợp dữ liệu.
     * Được inject tự động thông qua constructor (Lombok @RequiredArgsConstructor).
     */
    private final AnalyticsService analyticsService;

    /**
     * API lấy dữ liệu dashboard / summary / charts.
     * Một method nhưng map nhiều endpoint:
     *  - /api/v1/analytics/dashboard
     *  - /api/v1/analytics/summary
     *  - /api/v1/analytics/charts
     * @return DashboardResponseDTO chứa dữ liệu tổng hợp cho dashboard
     */
    @GetMapping({"/dashboard", "/summary", "/charts"})
    @Operation(summary = "Get analytics dashboard")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public DashboardResponseDTO dashboard() {
        // Gọi service để lấy dữ liệu dashboard
        return analyticsService.getDashboard();
    }
}

