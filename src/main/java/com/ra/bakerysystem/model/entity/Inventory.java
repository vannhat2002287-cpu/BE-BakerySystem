// 名前: Tram, Uyen
package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Inventory Entity đại diện cho bảng "inventories" trong database.
 * Mỗi sản phẩm (Product) sẽ có đúng 1 bản ghi Inventory.
 * Bảng inventories dùng product_id vừa là:
 *  - Khóa chính
 *  - Khóa ngoại liên kết tới bảng products
 */

@Entity
@Table(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    /**
     * ID của sản phẩm.
     * Đồng thời là PRIMARY KEY của bảng inventories.
     */
    @Id
    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long productId;

    /**
     * Quan hệ 1-1 với Product.
     * @MapsId:
     *  - Dùng chung khóa chính với Product
     *  - product_id vừa là PK vừa là FK
     * @JsonIgnore:
     *  - Tránh vòng lặp JSON khi serialize (Product -> Inventory -> Product)
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(name = "current_quantity", nullable = false)
    @JsonProperty("current_quantity")
    private Integer currentQuantity = 0; //  Số lượng tồn kho hiện tại của sản phẩm.

    @Column(name = "min_threshold")
    @JsonProperty("min_threshold")
    private Integer minThreshold;  // Ngưỡng cảnh báo tồn kho thấp.

    @Column(name = "last_updated")
    @JsonProperty("last_updated")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") // Khớp định dạng ISO string
    private LocalDateTime lastUpdated; // Thời điểm cập nhật tồn kho gần nhất.

    /**
     * Tự động cập nhật lastUpdated mỗi khi:
     *  - Insert (PrePersist)
     *  - Update (PreUpdate)
     */
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}