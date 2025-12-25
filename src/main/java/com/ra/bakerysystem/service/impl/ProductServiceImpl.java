package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.model.DTO.ProductDTO;
import com.ra.bakerysystem.model.entity.Product;
import com.ra.bakerysystem.repository.ProductRepository;
import com.ra.bakerysystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> findAllActive() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<ProductDTO> findByCategory(String categoryId) {
        return productRepository.findByCategory_IdAndActiveTrue(categoryId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Convert Entity => DTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setType(product.getType().name());
        dto.setAlcoholic(product.getAlcoholic());
        dto.setImageUrl(product.getImageUrl());


        return dto;
    }
}
