// 名前: Tram, Nhat
package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import com.ra.bakerysystem.model.DTO.FactoryRequestDTO;
import com.ra.bakerysystem.model.entity.FactoryRequest;
import com.ra.bakerysystem.service.FactoryRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * FactoryRequestController quản lý các API liên quan đến Factory Request
 * Giúp:
 *  - Gửi yêu cầu sản xuất / nhập hàng tới nhà máy
 *  - Theo dõi và cập nhật trạng thái xử lý của các yêu cầu này
 */

@RestController
@RequestMapping("/api/v1/factory-requests")
@RequiredArgsConstructor
@Tag(name = "Factory Request API")

public class FactoryRequestController {

    // Service xử lý nghiệp vụ liên quan đến Factory Request.
    private final FactoryRequestService factoryRequestService;

    /**
     * API tạo mới một Factory Request.
     * URL: POST /api/v1/factory-requests
     * @param dto dữ liệu yêu cầu gửi tới nhà máy
     * @return FactoryRequest vừa được tạo
     */
    // POST /factory-requests
    @PostMapping
    @Operation(summary = "Create factory request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })

    public FactoryRequest create(
            @RequestBody FactoryRequestDTO dto
    ) {
        // Gọi service để tạo mới Factory Request
        return factoryRequestService.create(dto);
    }

    /**
     * API lấy danh sách tất cả Factory Request.
     * URL: GET /api/v1/factory-requests
     * @return danh sách FactoryRequest
     */
    // GET /factory-requests
    @GetMapping
    @Operation(summary = "Get all factory requests")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<FactoryRequest> getAll() {
        // Gọi service để lấy danh sách Factory Request
        return factoryRequestService.getAll();
    }

    /**
     * API cập nhật trạng thái của Factory Request.
     * URL: PATCH /api/v1/factory-requests/{id}/status
     * @param requestId id của Factory Request
     * @param status    trạng thái mới (PENDING, DELIVERED, CANCELLED)
     * @return FactoryRequest sau khi được cập nhật
     */
    // PATCH /factory-requests/{id}/status
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update factory request status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })
    public FactoryRequest updateStatus(
            @PathVariable("id") Long requestId,
            @RequestParam FactoryRequestStatus status
    ) {
        // Gọi service để cập nhật trạng thái Factory Request
        return factoryRequestService.updateStatus(requestId, status);
    }
}
