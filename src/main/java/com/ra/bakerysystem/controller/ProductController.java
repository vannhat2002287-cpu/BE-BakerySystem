package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.ProductDTO;
import com.ra.bakerysystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.findAllActive();
    }

    @GetMapping("/category/{id}")
    public List<ProductDTO> getByCategory(@PathVariable String id) {
        return productService.findByCategory(id);
    }
}

