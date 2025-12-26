package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.bakerysystem.common.OrderType;
import com.ra.bakerysystem.common.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", length = 50)
    @JsonProperty("order_id")
    private String id;

    @Column(name = "order_time", updatable = false) // Không cho phép cập nhật lại thời gian sau khi đã tạo
    @JsonProperty(value = "order_time", access = JsonProperty.Access.READ_ONLY) // Frontend chỉ đọc, không được ghi đè
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    @JsonProperty("order_type")
    private OrderType orderType;

    @Column(name = "total_amount")
    @JsonProperty("total_amount")
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_received")
    @JsonProperty("payment_received")
    private Integer paymentReceived;

    @Column(name = "change_amount")
    @JsonProperty("change_amount")
    private Integer changeAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("items")
    private List<OrderItem> items;

    @PrePersist
    protected void onCreate() {
        // Luôn gán thời gian hiện tại của server khi lưu vào DB
        this.orderTime = LocalDateTime.now();
    }
}