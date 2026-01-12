// 名前: Tram, Thuy
package com.ra.bakerysystem.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * PopularProductDTO (Data Transfer Object) dùng để:
 *  - Trả thông tin các sản phẩm bán chạy
 *  - Phục vụ hiển thị trên dashboard / analytics
 */

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PopularProductDTO {
    private Long productId;      // ID của sản phẩm.
    private String name;         // Tên của sản phẩm.
    private Long soldQuantity;   // Tổng số lượng đã bán của sản phẩm.
}

