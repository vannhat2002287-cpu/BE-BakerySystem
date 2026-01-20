package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    private Long productId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(name = "default_quantity")
    private Integer defaultQuantity;

    @Column(name = "current_quantity", nullable = false)
    private Integer currentQuantity; //Số lượng tồn kho hiện tại

    @Column(name = "reorder_point", nullable = false)
    private Integer reorderPoint; //Ngưỡng tồn kho tối thiểu

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;//Thời điểm inventory được cập nhật gần nhất
    //map FE dùng field default_quantity
    @JsonProperty("default_quantity")
    public Integer getDefaultQuantity() {
        return currentQuantity;
    }
//Lifecycle callback của JPA, prepersist Gọi trước khi INSERT, PreUpdate Gọi trước khi UPDATE
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
}