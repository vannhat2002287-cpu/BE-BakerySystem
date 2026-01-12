// 名前: Tram, Nhat
package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.bakerysystem.model.entity.Category;
import lombok.*;

/**
 * CategoryDTO (Data Transfer Object) dùng để:
 *  - Trả dữ liệu Category cho client (frontend)
 *  - Tránh expose trực tiếp Entity ra ngoài
 *  - Kiểm soát cấu trúc JSON response
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    /**
     * ID của category.
     * Được map sang JSON với key là "category_id".
     */
    @JsonProperty("category_id")
    private Long id;     // ID của category.
    private String name; // Tên của category.

    /**
     * Constructor dùng để convert từ Category Entity sang CategoryDTO.
     * @param category entity Category từ database
     */
    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
