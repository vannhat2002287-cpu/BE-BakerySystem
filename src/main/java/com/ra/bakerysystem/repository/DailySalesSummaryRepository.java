package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.DailySalesSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Repository thao tác với bảng daily_sales_summary
 *
 * Bảng này dùng để:
 * - Lưu dữ liệu tổng hợp số lượng bán theo ngày
 * - Phục vụ thống kê / phân tích (analytics, production planning)
 */
public interface DailySalesSummaryRepository
        extends JpaRepository<DailySalesSummary, Long> {
    /**
     * Tính trung bình số lượng bán mỗi ngày của một sản phẩm
     *
     * Dữ liệu lấy từ bảng daily_sales_summary:
     * - Mỗi record tương ứng 1 sản phẩm trong 1 ngày
     * - totalSold = tổng số bán trong ngày đó
     *
     * JPQL tương đương:
     * SELECT AVG(d.totalSold)
     * FROM DailySalesSummary d
     * WHERE d.productId = :productId
     *
     * Method này thường dùng cho:
     * - Dự đoán nhu cầu sản xuất
     * - Tính số lượng cần đặt hàng từ factory
     *
     * @param productId ID của sản phẩm
     * @return trung bình số lượng bán mỗi ngày (có thể null nếu chưa có dữ liệu)
     */
    @Query("""
    SELECT COALESCE(AVG(d.totalSold), 0)
    FROM DailySalesSummary d
    WHERE d.productId = :productId
""")
    Double calculateAverageDailySales(@Param("productId") String productId);
}