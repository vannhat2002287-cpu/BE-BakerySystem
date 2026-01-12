// 名前: Tram, Nhat
package com.ra.bakerysystem.model.DTO;

import lombok.Data;

/**
 * OrderItemRequestDTO dùng để:
 *  - Nhận thông tin từng sản phẩm khi client tạo đơn hàng
 *  - Truyền dữ liệu từ Controller xuống Service
 */

@Data
public class OrderItemRequestDTO {
    private Long productId;     // ID của sản phẩm được đặt hàng.
    private Integer quantity;   // Số lượng sản phẩm khách hàng đặt.
}
