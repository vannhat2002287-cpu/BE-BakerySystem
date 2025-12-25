package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.OrderRequestDTO;
import com.ra.bakerysystem.model.DTO.OrderResponseDTO;
import com.ra.bakerysystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request) {
        return orderService.createOrder(request);
    }
}

