package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ra.bakerysystem.common.FactoryRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "factory_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FactoryRequest {

    /**
     * ID chính của yêu cầu sản xuất (PK)
     * - Auto increment
     * - Dùng để update status, delivered quantity, tracking request
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;
    /**
     * ID sản phẩm cần sản xuất
     * - Lưu dạng Long thay vì @ManyToOne để:
     *   + Tránh join nặng
     *   + Phù hợp với logic batch / scheduler
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;
//Tên sản phẩm tại thời điểm tạo request (snapshot)
    @Column(name = "product_name", nullable = false)
    private String productName;
    //Số lượng yêu cầu sản xuất ban đầu
    @Column(name = "request_quantity", nullable = false)
    private Integer requestQuantity;
//Số lượng đã được giao từ factory
    @Column(name = "delivered_quantity", nullable = false)
    private Integer deliveredQuantity = 0;
//Ngày kinh doanh (business date)
    @Column(name = "business_date", nullable = false)
    private LocalDate businessDate;
//Thời điểm tạo request trong hệ thống
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
//ETA - Thời gian dự kiến factory giao hàng
    @Column(name = "eta_at", nullable = false)
    private LocalDateTime etaAt;
//Ghi chú nội bộ cho factory request
    @Column(columnDefinition = "TEXT")
    private String note;
    /**
     * Trạng thái của factory request
     *
     * CREATED            : Mới tạo, chưa giao gì
     * PARTIALLY_DELIVERED: Đã giao một phần
     * DELIVERED          : Giao đủ
     * CANCELLED          : Hủy request
     *
     * Lưu dạng STRING để:
     * - Dễ đọc DB
     * - Tránh lỗi khi đổi thứ tự enum
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FactoryRequestStatus status;
}

