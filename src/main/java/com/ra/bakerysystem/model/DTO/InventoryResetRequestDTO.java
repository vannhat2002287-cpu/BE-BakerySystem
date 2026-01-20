package com.ra.bakerysystem.model.DTO;

import lombok.Data;
import java.util.List;

@Data
public class InventoryResetRequestDTO {
    private List<Long> productIds;
}
