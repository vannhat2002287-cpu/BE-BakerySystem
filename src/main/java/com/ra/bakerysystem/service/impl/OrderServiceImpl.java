// 名前: Tram, Nhat
package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.common.ProductType;
import com.ra.bakerysystem.common.OrderType;

import com.ra.bakerysystem.model.DTO.OrderItemRequestDTO;
import com.ra.bakerysystem.model.DTO.OrderRequestDTO;
import com.ra.bakerysystem.model.entity.*;
import com.ra.bakerysystem.repository.InventoryRepository;
import com.ra.bakerysystem.repository.OrderRepository;
import com.ra.bakerysystem.repository.ProductRepository;
import com.ra.bakerysystem.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    /**
     * Tạo mới một đơn hàng
     *  1. Validate các rule nghiệp vụ theo thời gian
     *  2. Tạo Order entity
     *  3. Xử lý từng OrderItem:
     *     - Validate product
     *     - Check rule bán rượu
     *     - Check & trừ tồn kho
     *     - Snapshot dữ liệu product vào OrderItem
     *  4. Tính tổng tiền và tiền thừa
     *  5. Lưu Order vào database
     */

    @Override
    @Transactional
    public Order createOrder(OrderRequestDTO dto) {
        // Thời điểm hiện tại để validate rule theo giờ
        LocalDateTime now = LocalDateTime.now();

        // 1. Validate Eat-in sau 20:30
        if (dto.getOrderType() == OrderType.EAT_IN
                && now.toLocalTime().isAfter(LocalTime.of(20, 30))) {
            throw new RuntimeException("Eat-in is not allowed after 20:30");
        }

        // 2. Tạo Order entity
        Order order = new Order();
        order.setOrderType(dto.getOrderType());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setPaymentReceived(dto.getPaymentReceived());
        order.setItems(new ArrayList<>());

        int totalAmount = 0;

        // 3. Xử lý từng item
        for (OrderItemRequestDTO itemDTO : dto.getItems()) {

            // 3.1 Lấy product
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found: " + itemDTO.getProductId())
                    );

            // 3.2 Validate không cho bán rượu trước 17:00
            if (Boolean.TRUE.equals(product.getAlcoholic())
                    && now.toLocalTime().isBefore(LocalTime.of(17, 0))) {
                throw new RuntimeException("Alcohol is not allowed before 17:00");
            }

            // 3.3 Check tồn kho và trừ kho
                Inventory inventory = inventoryRepository.findById(product.getId())
                        .orElseThrow(() ->
                                new RuntimeException("Inventory not found for product: " + product.getId())
                        );

                if (inventory.getCurrentQuantity() < itemDTO.getQuantity()) {
                    throw new RuntimeException(
                            "Not enough stock for product: " + product.getName()
                    );
                }

            // Trừ tồn kho
                inventory.setCurrentQuantity(
                        inventory.getCurrentQuantity() - itemDTO.getQuantity()
                );
                inventoryRepository.save(inventory);


            // 3.4 Snapshot OrderItem
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setName(product.getName());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(product.getPrice());

            order.getItems().add(item);

            // Cộng dồn tổng tiền
            totalAmount += product.getPrice() * itemDTO.getQuantity();
        }

        // 4. Tính tiền
        order.setTotalAmount(totalAmount);
        order.setChangeAmount(dto.getPaymentReceived() - totalAmount);

        // 5. Save order
        return orderRepository.save(order);
    }

    // Lấy danh sách đơn hàng theo ngày
    @Override
    public List<Order> getOrdersByDate(LocalDate date, OrderType type) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        if (type != null) {
            return orderRepository.findByOrderTimeBetweenAndOrderType(
                    start, end, type
            );
        }

        return orderRepository.findByOrderTimeBetween(start, end);
    }

    // Lấy chi tiết đơn hàng theo ID
    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
