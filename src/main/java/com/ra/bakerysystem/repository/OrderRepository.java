package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

