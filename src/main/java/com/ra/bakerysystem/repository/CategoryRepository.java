package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
//Repository cho bảng categories
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
