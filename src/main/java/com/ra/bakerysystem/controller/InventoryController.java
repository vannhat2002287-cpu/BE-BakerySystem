package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.InventoryResetRequestDTO;
import com.ra.bakerysystem.model.entity.Inventory;
import com.ra.bakerysystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory API")
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Reset tồn kho hằng ngày
     */
    @PostMapping("/reset-daily")
    @Operation(summary = "Reset inventory to default quantity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reset success")
    })
    public ResponseEntity<Void> resetDailyInventory(
            @RequestBody InventoryResetRequestDTO request
    ) {
        inventoryService.resetDailyInventory(request.getProductIds());
        return ResponseEntity.ok().build();
    }

    /**
     * API chính – điều chỉnh tồn kho theo productId (FE dùng cái này)
     * PATCH /api/v1/inventory/{productId}?currentQuantity=10
     */
    @PatchMapping("/{productId}")
    @Operation(summary = "Adjust inventory quantity")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public Inventory adjustInventory(
            @PathVariable Long productId,
            @RequestParam(name = "currentQuantity") Integer currentQuantity
    ) {
        log.info("Adjust inventory: productId={}, quantity={}", productId, currentQuantity);
        return inventoryService.adjustInventory(productId, currentQuantity);
    }

    /**
     * Fallback API – khi FE gửi productId = undefined
     * PATCH /api/v1/inventory/undefined?currentQuantity=66
     */
    @PatchMapping("/undefined")
    public ResponseEntity<?> adjustInventoryWhenUndefined(
            @RequestParam(name = "currentQuantity", required = false) Integer currentQuantity
    ) {
        log.warn("FE sent productId=undefined, inventory adjust skipped. quantity={}", currentQuantity);
        // Không làm gì, chỉ cho FE đi tiếp
        return ResponseEntity.ok().build();
    }

    /**
     * Lấy toàn bộ danh sách tồn kho
     */
    @GetMapping
    @Operation(summary = "Get all inventory")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<Map<String, Object>> getAllInventory() {
        return inventoryService.getAllInventory()
                .stream()
                .map(inv -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("product_id", inv.getProduct().getId());
                    map.put("current_quantity", inv.getCurrentQuantity());
                    map.put("default_quantity", inv.getDefaultQuantity());
                    map.put("last_updated", inv.getLastUpdated());
                    return map;
                })
                .toList();
    }
}
