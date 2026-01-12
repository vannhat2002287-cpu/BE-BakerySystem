// 名前: Tram, Nhat
package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

/**
 * Category Entity đại diện cho bảng "categories" trong database.
 * Entity này dùng để:
 *  - Mapping dữ liệu giữa database và ứng dụng
 *  - Quản lý thông tin danh mục sản phẩm
 */

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    /**
     * Khóa chính của bảng categories.
     * Tự động tăng (AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("category_id") // Để khớp với file type.ts
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Tên của category. Không được null, không được trùng nhau
}