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
 * Quản lý các API liên quan đến Category (danh mục sản phẩm)
 * Nhận request từ client và trả về dữ liệu category
 */

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category API")

public class CategoryController {

    private final CategoryService categoryService;

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
