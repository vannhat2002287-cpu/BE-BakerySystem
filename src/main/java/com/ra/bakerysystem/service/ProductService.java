package com.ra.bakerysystem.service;

import com.ra.bakerysystem.model.DTO.ProductRequest;
import com.ra.bakerysystem.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductRequest> findByCategory(Long categoryId);
    Product save(ProductRequest productRequest, String imagePath, Long id);
    List<Product> findAllActiveProducts();

}

