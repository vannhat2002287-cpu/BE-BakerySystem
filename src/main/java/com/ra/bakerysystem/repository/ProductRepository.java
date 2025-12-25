package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByActiveTrue();

    List<Product> findByCategory_IdAndActiveTrue(String categoryId);
}
