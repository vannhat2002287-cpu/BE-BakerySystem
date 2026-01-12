// 名前: Tram, Nhat
package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

/**
 * OrderItem Entity đại diện cho bảng "order_items" trong database.
 * Mỗi OrderItem tương ứng với:
 *  - 1 sản phẩm
 *  - 1 số lượng
 *  - 1 giá bán tại thời điểm đặt hàng
 * => bảng trung gian giữa Order và Product.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore // ID của bảng trung gian => không cần gửi về Frontend
    private Long id; // ID nội bộ của OrderItem.

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order; // Đơn hàng mà OrderItem thuộc về.

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product; //  Sản phẩm tương ứng với OrderItem.

    @Column(nullable = false)
    private String name; // Tên sản phẩm tại thời điểm bán (Snapshot)

    @Column(nullable = false)
    private Integer quantity; // Số lượng sản phẩm trong đơn.

    @Column(name = "unit_price", nullable = false)
    @JsonProperty("unit_price")
    private Integer unitPrice; // Giá bán của 1 đơn vị sản phẩm tại thời điểm đặt hàng.

     /**
       * Trả về product_id cho frontend.
       * Không expose toàn bộ Product object.
       */
    @JsonGetter("product_id")
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }

    /**
     * Sub total = unitPrice * quantity.
     * Helper field, không lưu trong database.
     */
    @JsonGetter("sub_total")
    public Integer getSubTotal() {
        if (unitPrice == null || quantity == null) {
            return 0;
        }
        return unitPrice * quantity;
    }
}