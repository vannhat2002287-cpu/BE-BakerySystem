// 名前: Tram, Thuy
package com.ra.bakerysystem.model.DTO;

import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

/**
 * DashboardResponseDTO dùng để trả dữ liệu thống kê tổng hợp cho màn hình dashboard của hệ thống Bakery System.
 * DTO này thường được sử dụng bởi:
 *  - AnalyticsController
 *  - Dashboard / Chart UI trên frontend
 */

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DashboardResponseDTO {

    private Integer dailySales;                      // Tổng doanh thu trong ngày
    private Integer orderCount;                      // Tổng số đơn hàng trong ngày.
    private Integer lowStockCount;                   // Số lượng sản phẩm đang ở mức tồn kho thấp.

    private List<Integer> hourlySales;               // Doanh thu theo từng giờ trong ngày (phục vụ biểu đồ).
    private List<PopularProductDTO> popularProducts; // Danh sách các sản phẩm bán chạy.
}
