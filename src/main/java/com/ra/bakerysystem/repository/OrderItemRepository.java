// 名前: Tram, Nhat
package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Lấy danh sách các sản phẩm bán chạy nhất

    @Query("""
        SELECT oi.product.id, oi.product.name, SUM(oi.quantity)
        FROM OrderItem oi
        GROUP BY oi.product.id, oi.product.name
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<Object[]> findTopProducts(Pageable pageable);
}
