package com.ra.bakerysystem.config;

import com.ra.bakerysystem.common.ProductType;
import com.ra.bakerysystem.model.entity.Category;
import com.ra.bakerysystem.model.entity.Inventory;
import com.ra.bakerysystem.model.entity.Product;
import com.ra.bakerysystem.repository.CategoryRepository;
import com.ra.bakerysystem.repository.InventoryRepository;
import com.ra.bakerysystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;

    @Override
    public void run(String... args) {

        if (categoryRepo.count() > 0) return;

        Category bread = categoryRepo.save(
                Category.builder()
                        .name("Bread")
                        .build()
        );

        Product p = productRepo.save(
                Product.builder()
                        .name("Croissant")
                        .price(280)
                        .type(ProductType.food)
                        .active(true)
                        .category(bread)
                        .build()
        );


        inventoryRepo.save(
                Inventory.builder()
                        .product(p)
                        .currentQuantity(50)
                        .minThreshold(10)
                        .lastUpdated(LocalDateTime.now())
                        .build()
        );
    }
}

