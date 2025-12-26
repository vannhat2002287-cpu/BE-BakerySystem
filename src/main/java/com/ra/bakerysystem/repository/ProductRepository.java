package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

    List<Product> findByCategory_IdAndActiveTrue(Long categoryId);
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
}
