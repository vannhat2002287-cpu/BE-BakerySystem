// 名前: Tram, Uyen
package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.entity.Inventory;
import com.ra.bakerysystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * InventoryController quản lý các API liên quan đến tồn kho.
 * Giúp:
 *  - Xem danh sách tồn kho của tất cả sản phẩm
 *  - Điều chỉnh (tăng/giảm) số lượng tồn kho của từng sản phẩm
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory API")
public class InventoryController {

    // Service xử lý nghiệp vụ liên quan đến Inventory.
    private final InventoryService inventoryService;

    /**
     * API lấy danh sách tồn kho của tất cả sản phẩm.
     * URL: GET /api/v1/inventory
     * @return danh sách Inventory
     */
    // GET /api/v1/inventory
    @GetMapping
    @Operation(summary = "Get all inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<Inventory> getAllInventory() {
        // Gọi service để lấy danh sách tồn kho
        return inventoryService.getAllInventory();
    }

    /**
     * API điều chỉnh số lượng tồn kho của một sản phẩm.
     * URL: PATCH /api/v1/inventory/{productId}
     * @param productId       id của sản phẩm
     * @param currentQuantity số lượng cần điều chỉnh (có thể tăng hoặc giảm)
     * @return Inventory sau khi được cập nhật
     */
    // PATCH /api/v1/inventory/{productId}
    @PatchMapping("/{productId}")
    @Operation(summary = "Adjust inventory quantity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Inventory adjustInventory(
            @PathVariable Long productId,
            @RequestParam(name = "currentQuantity", defaultValue = "0") Integer currentQuantity
    ) {

        // Ghi log để theo dõi việc điều chỉnh tồn kho
        log.info("Adjusting inventory quantity: {}", currentQuantity);
        // Gọi service để điều chỉnh tồn kho
        return inventoryService.adjustInventory(productId, currentQuantity);
    }
}