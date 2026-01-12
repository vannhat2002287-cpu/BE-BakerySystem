// 名前: Tram, Uyen
package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.model.DTO.InventoryDTO;
import com.ra.bakerysystem.model.entity.Inventory;
import com.ra.bakerysystem.repository.InventoryRepository;
import com.ra.bakerysystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    /**
     * Điều chỉnh số lượng tồn kho của một sản phẩm
     * currentQuantity là số lượng MỚI (set trực tiếp), không phải số lượng tăng / giảm
     */

    @Override
    public Inventory adjustInventory(Long productId, Integer currentQuantity) {

        // Lấy inventory theo productId
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product_id=" + productId));

        // Cập nhật số lượng tồn kho mới
        inventory.setCurrentQuantity(currentQuantity);

        // Lưu lại inventory sau khi cập nhật
        return inventoryRepository.save(inventory);
    }

}