package com.ra.bakerysystem.service;

import com.ra.bakerysystem.model.DTO.InventoryDTO;
import java.util.List;

public interface InventoryService {

    List<InventoryDTO> getAll();
    void deductStock(String productId, int quantity);

}

