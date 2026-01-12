// 名前: Tram, Uyen
package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.ProductDTO;
import com.ra.bakerysystem.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * ProductController quản lý các API liên quan đến sản phẩm.
 * Giúp:
 *  - Lấy danh sách sản phẩm (có hỗ trợ filter)
 *  - Xem chi tiết thông tin một sản phẩm
 */

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product API")
public class ProductController {

    // Service xử lý nghiệp vụ liên quan đến Product
    private final ProductService productService;

    /**
     * API lấy danh sách sản phẩm với các điều kiện lọc.
     * URL: GET /api/v1/products
     * @param categoryId lọc theo id danh mục (không bắt buộc)
     * @param search     tìm kiếm theo tên sản phẩm (không bắt buộc)
     * @param isActive   lọc theo trạng thái hoạt động của sản phẩm (không bắt buộc)
     * @return danh sách ProductDTO
     */
    @GetMapping
    @Operation(summary = "Get product list")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<ProductDTO> getProducts(
            @RequestParam(value = "category_id", required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(value = "is_active", required = false) Boolean isActive
    ) {
        // Gọi service để lấy danh sách sản phẩm theo điều kiện filter
        return productService.getProducts(categoryId, search, isActive);
    }


    /**
     * API lấy chi tiết thông tin của một sản phẩm.
     * URL: GET /api/v1/products/{id}
     * @param id id của sản phẩm
     * @return ProductDTO chứa thông tin chi tiết sản phẩm
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    public ProductDTO getProductDetail(@PathVariable Long id) {
        // Gọi service để lấy thông tin chi tiết sản phẩm
        return productService.getProductById(id);
    }
}
