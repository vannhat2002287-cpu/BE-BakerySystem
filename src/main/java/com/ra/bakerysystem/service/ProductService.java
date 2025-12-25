package com.ra.bakerysystem.service;

import com.ra.bakerysystem.model.DTO.ProductDTO;
import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllActive();
    List<ProductDTO> findByCategory(String categoryId);
}

