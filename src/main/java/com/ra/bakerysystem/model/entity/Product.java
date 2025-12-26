package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.bakerysystem.common.ProductType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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
    private Long id;
   private String productCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType type; // Đảm bảo Enum đã có: food, drink, alcohol, merchandise

    @Column(name = "is_alcoholic")
    @JsonProperty("is_alcoholic")
    private Boolean alcoholic = false;

    @Column(name = "image", columnDefinition = "LONGTEXT")
    @JsonProperty("image")
    private String img;

    @Column(name = "is_active")
    @JsonProperty("is_active")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnore // Không trả về cả object Category để tránh vòng lặp JSON
    private Category category;

    // Helper method để trả về category_id đúng như frontend cần
    @JsonGetter("category_id")
    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore // Inventory thường được quản lý ở API riêng hoặc trả về riêng
    private Inventory inventory;

    public static String generateOrderCode() {
        return "PD" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
    }
}