// 名前: Tram, Nhat
package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.model.DTO.CategoryDTO;
import com.ra.bakerysystem.model.entity.Category;
import com.ra.bakerysystem.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * CategoryController quản lý các API liên quan đến danh mục sản phẩm.
 * Giúp:
 *  - Nhận request từ client (frontend)
 *  - Gọi CategoryService để xử lý business logic
 *  - Trả về dữ liệu Category dưới dạng DTO
 */

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category API")

public class CategoryController {

    /**
     * Service xử lý logic liên quan đến Category.
     * Được inject thông qua constructor (Lombok @RequiredArgsConstructor).
     */
    private final CategoryService categoryService;

    /**
     * API lấy danh sách tất cả category.
     * URL: GET /api/v1/categories
     * @return danh sách CategoryDTO
     */
    @GetMapping
    @Operation(summary = "Get all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<CategoryDTO> getAllCategories() {
        // Gọi service để lấy toàn bộ danh sách category
        return categoryService.getAllCategories();
    }
}
