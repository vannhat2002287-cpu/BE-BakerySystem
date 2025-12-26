package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.model.DTO.InventoryDTO;
import com.ra.bakerysystem.model.entity.Inventory;
import com.ra.bakerysystem.repository.InventoryRepository;
import com.ra.bakerysystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryDTO> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public void deductStock(String productId, int quantity) {

        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found for product " + productId)
                );

        int remain = inventory.getCurrentQuantity() - quantity;

        if (remain < 0) {
            throw new RuntimeException("Not enough stock for product " + productId);
        }

        inventory.setCurrentQuantity(remain);
        // lastUpdated sẽ tự cập nhật nhờ @PreUpdate
        inventoryRepository.save(inventory);
    }

    // Convert Entity => DTO
    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setProductId(inventory.getProductId());
        dto.setCurrentQuantity(inventory.getCurrentQuantity());
        dto.setMinThreshold(inventory.getMinThreshold());
        dto.setLastUpdated(inventory.getLastUpdated());

        return dto;
    }
}
