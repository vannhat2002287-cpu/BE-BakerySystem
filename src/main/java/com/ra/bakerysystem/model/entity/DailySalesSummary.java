
package com.ra.bakerysystem.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


import java.time.LocalDate;
/**
 * Entity đại diện cho bảng daily_sales_summary
 *
 * Dùng để lưu dữ liệu TỔNG HỢP doanh số theo NGÀY và theo SẢN PHẨM
 * Phục vụ cho:
 *    - Thống kê
 *    - Biểu đồ
 *    - Analytics dashboard
 *
 * Đây KHÔNG phải dữ liệu order gốc
 *    mà là dữ liệu đã được tính toán và lưu sẵn
 */
@Entity
@Table(name = "daily_sales_summary")
public class DailySalesSummary {
    /**
     * Khóa chính của bảng summary
     * Auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID sản phẩm
     * → dùng để biết doanh số của sản phẩm nào
     * (map với products.product_id)
     */

    private String productId;
    /**
     * Ngày kinh doanh (YYYY-MM-DD)
     * → dùng để group dữ liệu theo ngày
     */

    private LocalDate businessDate;
    /**
     * Tổng số lượng bán được trong ngày
     * của sản phẩm này
     */

    private Integer totalSold;
}
