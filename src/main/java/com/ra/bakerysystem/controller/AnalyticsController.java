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

/**
 * Quản lý các API liên quan đến Analytics / Dashboard
 * Cung cấp dữ liệu tổng hợp cho màn hình thống kê (dashboard, summary, charts)
 */

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics API")

public class AnalyticsController {

    private final AnalyticsService analyticsService;

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

