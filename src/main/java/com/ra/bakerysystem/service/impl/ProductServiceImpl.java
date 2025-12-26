package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.common.ProductType;
import com.ra.bakerysystem.model.DTO.ProductRequest;
import com.ra.bakerysystem.model.entity.Category;
import com.ra.bakerysystem.model.entity.Product;
import com.ra.bakerysystem.repository.CategoryRepository;
import com.ra.bakerysystem.repository.ProductRepository;
import com.ra.bakerysystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Value("$path-upload")
    private String imagePath;


    @Override
    public List<ProductRequest> findByCategory(Long categoryId) {
        return List.of();
    }

    @Override
    public Product save(ProductRequest request, String imagePath, Long id) {

        try {
            // 1. Kiểm tra trùng tên
            Optional<Product> productByName = productRepository.findByName(request.getName());
            if (productByName.isPresent()) {
                if (id == null || !productByName.get().getId().equals(id)) {
                    throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
                }
            }

            // 2. Kiểm tra category
            if (request.getCategoryId() == null) {
                throw new IllegalArgumentException("Danh mục không được để trống");
            }

            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Danh mục không tồn tại"));

            // 3. Tạo mới hoặc cập nhật
            Product product;
            if (id == null) {
                product = new Product();
                product.setProductCode(Product.generateOrderCode());
            } else {
                product = productRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm"));
            }

            // 4. Gán dữ liệu
            product.setName(request.getName());
            product.setPrice(request.getPrice());
            product.setType(request.getType());
            product.setAlcoholic(
                    request.getType() == ProductType.alcohol
                            || Boolean.TRUE.equals(request.getAlcoholic())
            );
            product.setActive(
                    request.getActive() != null ? request.getActive() : true
            );
            product.setCategory(category);

            if (imagePath != null && !imagePath.isBlank()) {
                product.setImg(imagePath);
            }

            // 5. Lưu DB
            return productRepository.save(product);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    e
            );
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Lỗi hệ thống, vui lòng thử lại",
                    e
            );
        }
    }


}
