package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryDTO {

    private Long productId;
    private Integer currentQuantity;
    private Integer minThreshold;
    private LocalDateTime lastUpdated;
}

