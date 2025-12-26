package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private String id;
    private String name;
    private Integer price;
    private String type;
    private Boolean alcoholic;
    private String imageUrl;
    private Boolean active;
    private String categoryId;
}
