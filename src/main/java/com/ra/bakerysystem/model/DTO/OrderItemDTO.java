// 名前: Tram, Nhat
package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * OrderItemDTO (Data Transfer Object) dùng để:
 *  - Trả thông tin chi tiết từng sản phẩm trong một đơn hàng
 *  - Đại diện cho một dòng sản phẩm (item) trong Order
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {
    private Long productId;     // ID của sản phẩm.
    private String name;        // Tên sản phẩm.
    private Integer quantity;   // Số lượng sản phẩm trong đơn hàng.
    private Integer unitPrice;  // Giá bán của một đơn vị sản phẩm.
}

