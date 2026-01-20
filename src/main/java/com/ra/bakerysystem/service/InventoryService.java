package com.ra.bakerysystem.service;

import com.ra.bakerysystem.model.DTO.InventoryDTO;
import com.ra.bakerysystem.model.entity.Inventory;

import java.util.List;

public interface InventoryService {
    void resetDailyInventory(List<Long> productIds);

    List<Inventory> getAllInventory();

    Inventory adjustInventory(Long productId, Integer currentQuantity);
}
