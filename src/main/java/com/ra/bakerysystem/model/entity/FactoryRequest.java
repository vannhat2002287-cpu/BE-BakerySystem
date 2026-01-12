// 名前: Tram, Nhat
package com.ra.bakerysystem.model.entity;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

/**
 * FactoryRequest Entity đại diện cho bảng "factory_requests" trong database.
 * Entity này dùng để:
 *  - Lưu thông tin các yêu cầu sản xuất / nhập hàng gửi tới nhà máy
 *  - Theo dõi trạng thái xử lý của từng yêu cầu
 */

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
     * Khóa chính của bảng factory_requests.
     * Tự động tăng (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @Column(name = "product_id", nullable = false)
    private Long productId;   // ID của sản phẩm được yêu cầu sản xuất / nhập thêm.

    @Column(name = "product_name", nullable = false)
    private String productName;  // Tên của sản phẩm tại thời điểm tạo yêu cầu.

    @Column(name = "request_quantity", nullable = false)
    private Integer requestQuantity;  // Số lượng sản phẩm yêu cầu nhà máy cung cấp.

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;  // Thời điểm tạo Factory Request.

    @Column(name = "eta_at", nullable = false) // eta = estimate time arrival
    private LocalDateTime etaAt;  // Thời gian dự kiến nhận hàng (ETA - Estimated Time of Arrival).

    @Column(columnDefinition = "TEXT")
    private String note;  // Ghi chú thêm cho yêu cầu (nếu có).

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FactoryRequestStatus status;  // Trạng thái hiện tại của Factory Request.
}

