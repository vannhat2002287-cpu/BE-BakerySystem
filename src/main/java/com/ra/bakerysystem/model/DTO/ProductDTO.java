package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long id;
    private String name;
    private Integer price;
    private String type;
    private Boolean alcoholic;
    private String imageUrl;
    private Boolean active;
    private Long categoryId;
}
