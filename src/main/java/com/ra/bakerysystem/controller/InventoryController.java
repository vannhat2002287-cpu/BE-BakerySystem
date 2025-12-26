package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.InventoryDTO;
import com.ra.bakerysystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<InventoryDTO> getAll() {
        return inventoryService.getAll();
    }
}
