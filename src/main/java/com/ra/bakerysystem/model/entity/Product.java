// 名前: Tram, Uyen
package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.bakerysystem.common.ProductType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Product Entity đại diện cho bảng "products" trong database.
 * Đây là entity trung tâm của hệ thống:
 *  - Dùng cho bán hàng
 *  - Dùng cho quản lý tồn kho
 *  - Dùng cho báo cáo / thống kê
 */

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @JsonProperty("product_id")
    private Long id;  // ID của sản phẩm (PRIMARY KEY).

    @Column(nullable = false)
    private String name; // Tên sản phẩm.

    @Column(nullable = false)
    private Integer price; // Giá bán hiện tại của sản phẩm

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType type; // Đảm bảo Enum đã có: food, drink, alcohol

    @Column(name = "is_alcoholic")
    @JsonProperty("is_alcoholic")
    private Boolean alcoholic = false; // Đánh dấu mặc định là sản phẩm có cồn hay không (Mặc định = false)

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    @JsonProperty("image_url")
    private String imageUrl; // URL hình ảnh sản phẩm.

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private Boolean active = true; // Trạng thái hoạt động của sản phẩm.

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore // Không trả về cả object Category để tránh vòng lặp JSON
    private Category category; // Category mà sản phẩm thuộc về.

    // Helper method để trả về category_id đúng như frontend cần, không expose toàn bộ Category object.
    @JsonGetter("category_id")
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    /**
     * Quan hệ 1-1 với Inventory.
     * mappedBy = "product": phía Inventory là owner
     * cascade = ALL:
     *  - Tạo Product -> tự tạo Inventory (nếu gắn)
     *  - Xóa Product -> xóa Inventory
     */
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private Inventory inventory;
}