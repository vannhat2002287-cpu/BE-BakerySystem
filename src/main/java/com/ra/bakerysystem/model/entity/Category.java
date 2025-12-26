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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @JsonProperty("category_id") // Để khớp với file type.ts
    private Long id;

    @Column(nullable = false, unique = true) // Tên category không được trùng
    private String name;
}