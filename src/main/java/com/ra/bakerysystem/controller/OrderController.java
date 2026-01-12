// 名前: Tram, Nhat
package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.common.OrderType;
import com.ra.bakerysystem.model.DTO.OrderRequestDTO;
import com.ra.bakerysystem.model.entity.Order;
import com.ra.bakerysystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * OrderController quản lý các API liên quan đến đơn hàng.
 * Giúp:
 *  - Tạo đơn hàng mới
 *  - Lấy danh sách đơn hàng theo ngày và loại đơn
 *  - Xem chi tiết một đơn hàng
 */

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order API")
public class OrderController {

    // Service xử lý nghiệp vụ liên quan đến Order
    private final OrderService orderService;

    /**
     * API tạo mới một đơn hàng.
     * URL: POST /api/v1/orders
     * @param dto dữ liệu tạo đơn hàng
     * @return Order vừa được tạo
     */
    @PostMapping
    @Operation(summary = "Create order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public Order createOrder(@RequestBody OrderRequestDTO dto) {
        // Gọi service để tạo đơn hàng mới
        return orderService.createOrder(dto);
    }

    /**
     * API lấy danh sách đơn hàng theo ngày và loại đơn.
     * URL: GET /api/v1/orders
     * @param date ngày đặt đơn (định dạng: yyyy-MM-dd)
     * @param type loại đơn hàng (EAT_IN, TAKEAWAY) - không bắt buộc
     * @return danh sách Order
     */
    @GetMapping
    @Operation(summary = "Get orders by date")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<Order> getOrders(
            @RequestParam String date,
            @RequestParam(required = false) OrderType type
    ) {
        // Parse chuỗi ngày sang LocalDate
        // Gọi service để lấy danh sách đơn hàng theo ngày và loại
        return orderService.getOrdersByDate(
                LocalDate.parse(date),
                type
        );
    }

    /**
     * API lấy chi tiết một đơn hàng theo ID.
     * URL: GET /api/v1/orders/{id}
     * @param id id của đơn hàng
     * @return Order chi tiết
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public Order getOrderDetail(@PathVariable Long id) {
        // Gọi service để lấy thông tin chi tiết đơn hàng
        return orderService.getOrderById(id);
    }
}
