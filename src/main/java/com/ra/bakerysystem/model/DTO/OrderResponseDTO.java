package com.ra.bakerysystem.model.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponseDTO {

    private Long orderId;
    private LocalDateTime orderTime;
    private String orderType;
    private Integer totalAmount;
    private String paymentMethod;
    private Integer paymentReceived;
    private Integer changeAmount;
    private List<OrderItemDTO> items;
}

