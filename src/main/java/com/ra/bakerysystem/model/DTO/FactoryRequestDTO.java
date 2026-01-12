// 名前: Tram, Nhat
package com.ra.bakerysystem.model.DTO;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * FactoryRequestDTO (Data Transfer Object) dùng để:
 *  - Nhận dữ liệu từ client khi tạo yêu cầu gửi tới nhà máy
 *  - Truyền dữ liệu từ Controller xuống Service
 */

@Data
public class FactoryRequestDTO {
    private Long productId;            // ID của sản phẩm cần sản xuất hoặc nhập thêm.
    private Integer requestQuantity;   // Số lượng sản phẩm yêu cầu nhà máy cung cấp.
    private LocalDateTime etaAt;       // Thời gian dự kiến nhận hàng (ETA - Estimated Time of Arrival).
    private String note;               // Ghi chú thêm cho yêu cầu gửi tới nhà máy.
}
