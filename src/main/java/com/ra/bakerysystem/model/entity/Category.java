package com.ra.bakerysystem.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", length = 50)
    @JsonProperty("category_id") // Để khớp với file type.ts
    private String id;

    @Column(nullable = false, unique = true) // Tên category không được trùng
    private String name;
}