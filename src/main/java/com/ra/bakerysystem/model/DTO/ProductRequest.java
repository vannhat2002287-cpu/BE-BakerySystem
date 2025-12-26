package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ra.bakerysystem.common.ProductType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequest {

    private Long id;
    private String name;
    private Integer price;
    private ProductType type;
    private Boolean alcoholic;
    private String imageUrl;
    private Boolean active;
    private Long categoryId;
}
