package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
