// 名前: Tram, Nhat
package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.bakerysystem.common.OrderType;
import com.ra.bakerysystem.common.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order Entity đại diện cho bảng "orders" trong database.
 * Đơn hàng là entity trung tâm của hệ thống:
 *  - Chứa thông tin thanh toán
 *  - Phân loại đơn (ăn tại chỗ / mang đi)
 *  - Quản lý danh sách sản phẩm trong đơn
 */

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    /**
     * ID của đơn hàng (PRIMARY KEY).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @JsonProperty("order_id")
    private Long id;

    @Column(name = "order_time", updatable = false) // Không cho phép cập nhật lại thời gian sau khi đã tạo
    @JsonProperty(value = "order_time", access = JsonProperty.Access.READ_ONLY) // Frontend chỉ đọc, không được ghi đè
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")  // Luôn lấy thời gian server để đảm bảo tính chính xác
    private LocalDateTime orderTime; // Thời điểm tạo đơn hàng.

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    @JsonProperty("order_type")
    private OrderType orderType; // Loại đơn hàng

    @Column(name = "total_amount")
    @JsonProperty("total_amount")
    private Integer totalAmount; // Tổng tiền của đơn hàng.

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod; // Phương thức thanh toán

    @Column(name = "payment_received")
    @JsonProperty("payment_received")
    private Integer paymentReceived; // Số tiền khách đưa.

    @Column(name = "change_amount")
    @JsonProperty("change_amount")
    private Integer changeAmount; // Tiền trả lại cho khách.

    /**
     * Danh sách các sản phẩm trong đơn hàng.
     * mappedBy = "order": liên kết với field "order" trong OrderItem
     * cascade = ALL:
     *  - Lưu Order sẽ tự động lưu OrderItem
     *  - Xóa Order sẽ xóa toàn bộ OrderItem
     * orphanRemoval = true:
     *  - OrderItem không thuộc Order nào sẽ bị xóa
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("items")
    private List<OrderItem> items; // Danh sách các sản phẩm trong đơn hàng.

    @PrePersist
    protected void onCreate() {
        // Luôn gán thời gian hiện tại của server khi lưu vào DB
        this.orderTime = LocalDateTime.now();
    }
}