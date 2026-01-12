// 名前: Tram, Nhat
package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ra.bakerysystem.common.OrderType;
import com.ra.bakerysystem.common.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderRequestDTO (Data Transfer Object) dùng để:
 *  - Nhận dữ liệu từ client khi tạo đơn hàng
 *  - Truyền dữ liệu từ Controller xuống Service
 * DTO này chứa toàn bộ thông tin cần thiết để tạo một Order.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequestDTO {
    private OrderType orderType;              // Loại đơn hàng (Eat-in/ Take-away
    private LocalDateTime orderTime;          // Thời điểm tạo đơn hàng.
    private PaymentMethod paymentMethod;      // Phương thức thanh toán của đơn hàng
    private Integer paymentReceived;          // Số tiền khách hàng đưa khi thanh toán.
    private List<OrderItemRequestDTO> items;  // Danh sách các sản phẩm trong đơn hàng.
}