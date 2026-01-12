// 名前: Tram, Uyen
package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ra.bakerysystem.common.ProductType;
import com.ra.bakerysystem.model.entity.Product;
import lombok.*;

/**
 * ProductDTO (Data Transfer Object) dùng để:
 *  - Trả thông tin sản phẩm cho client
 *  - Tránh expose trực tiếp Product Entity
 *  - Kiểm soát cấu trúc JSON response
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    @JsonProperty("product_id")
    private Long id;               // ID của sản phẩm.
    private String name;           // Tên sản phẩm.
    private Integer price;         // Giá bán của sản phẩm.
    private ProductType type;      // Loại sản phẩm (FOOD, DRINK, ALCOHOL).

    @JsonProperty("is_alcoholic")
    private Boolean alcoholic;     // Đánh dấu sản phẩm có cồn hay không.

    @JsonProperty("image_url")
    private String imageUrl;       // URL hình ảnh của sản phẩm.

    @JsonProperty("is_active")
    private Boolean active;        // Trạng thái hoạt động của sản phẩm.

    @JsonProperty("category_id")
    private Long categoryId;       // ID của category mà sản phẩm thuộc về.

    /**
     * Constructor dùng để convert từ Product Entity sang ProductDTO.
     * @param product Product entity lấy từ database
     */
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.type = product.getType();
        this.alcoholic = product.getAlcoholic();
        this.imageUrl = product.getImageUrl();
        this.active = product.getActive();
        this.categoryId = product.getCategory() != null
                ? product.getCategory().getId()
                : null;
    }
}