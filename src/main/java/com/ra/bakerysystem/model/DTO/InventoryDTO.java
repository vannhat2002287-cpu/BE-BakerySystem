// 名前: Tram, Uyen
package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

/**
 * InventoryDTO (Data Transfer Object) dùng để:
 *  - Trả thông tin tồn kho của sản phẩm cho client
 *  - Tránh expose trực tiếp Inventory Entity
 *  - Kiểm soát dữ liệu hiển thị trên giao diện quản lý kho
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryDTO {

    private Long productId;             // ID của sản phẩm.
    private Integer currentQuantity;    // Số lượng tồn kho hiện tại của sản phẩm.
    private Integer minThreshold;       // Ngưỡng tồn kho tối thiểu.
    private LocalDateTime lastUpdated;  // Thời điểm cập nhật tồn kho gần nhất.
}

