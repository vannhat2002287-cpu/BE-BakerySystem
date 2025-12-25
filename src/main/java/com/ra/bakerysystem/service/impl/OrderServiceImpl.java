package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.common.OrderType;
import com.ra.bakerysystem.common.PaymentMethod;
import com.ra.bakerysystem.model.DTO.OrderRequestDTO;
import com.ra.bakerysystem.model.DTO.OrderResponseDTO;
import com.ra.bakerysystem.model.entity.Order;
import com.ra.bakerysystem.model.entity.OrderItem;
import com.ra.bakerysystem.model.entity.Product;
import com.ra.bakerysystem.repository.OrderRepository;
import com.ra.bakerysystem.service.InventoryService;
import com.ra.bakerysystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        // 1. Tạo Order
        Order order = new Order();
        order.setOrderType(OrderType.valueOf(request.getOrderType().toUpperCase()));
        order.setPaymentMethod(PaymentMethod.cash);

        // 2. Tạo OrderItem
        List<OrderItem> items = new ArrayList<>();
        int totalAmount = 0;

        for (var itemDTO : request.getItems()) {

            // Trừ kho
            inventoryService.deductStock(
                    itemDTO.getProductId(),
                    itemDTO.getQuantity()
            );

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());

            // Chỉ set Product với ID
            Product product = new Product();
            product.setId(itemDTO.getProductId());
            item.setProduct(product);

            totalAmount += item.getQuantity() * item.getUnitPrice();
            items.add(item);
        }

        // 3. Hoàn thiện Order
        order.setItems(items);
        order.setTotalAmount(totalAmount);

        int received = request.getPaymentReceived();
        order.setPaymentReceived(received);
        order.setChangeAmount(received - totalAmount);

        // 4. Lưu
        Order savedOrder = orderRepository.save(order);

        // 5. Convert sang ResponseDTO
        return convertToResponseDTO(savedOrder);
    }

    // Convert Entity => ResponseDTO
    private OrderResponseDTO convertToResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setOrderTime(order.getOrderTime());
        dto.setOrderType(order.getOrderType().name());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setPaymentMethod(order.getPaymentMethod().name());
        dto.setPaymentReceived(order.getPaymentReceived());
        dto.setChangeAmount(order.getChangeAmount());

        return dto;
    }
}
