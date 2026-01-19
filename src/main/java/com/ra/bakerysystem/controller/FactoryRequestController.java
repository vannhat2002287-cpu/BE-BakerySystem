package com.ra.bakerysystem.controller;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import com.ra.bakerysystem.model.DTO.FactoryRequestDTO;
import com.ra.bakerysystem.model.entity.FactoryRequest;
import com.ra.bakerysystem.service.FactoryRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/factory-requests")
@RequiredArgsConstructor
@Tag(name = "Factory Request API")
public class FactoryRequestController {

    private final FactoryRequestService factoryRequestService;

    // API AUTO (12:00 / 17:00)
    @PostMapping("/auto")
    @Operation(summary = "Auto create factory requests (12:00 / 17:00)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Processed")
    })
    public void autoCreateFactoryRequests() {
        factoryRequestService.autoCreateFactoryRequests();
    }

    // POST /factory-requests
    @PostMapping
    @Operation(summary = "Create factory request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public FactoryRequest create(
            @RequestBody FactoryRequestDTO dto
    ) {
        return factoryRequestService.create(dto);
    }

    // GET /factory-requests
    @GetMapping
    @Operation(summary = "Get all factory requests")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success")
    })
    public List<FactoryRequest> getAll() {
        return factoryRequestService.getAll();
    }

    // PATCH /factory-requests/{id}/status
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update factory request status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Request not found")
    })

    public FactoryRequest updateStatus(
            @PathVariable("id") Long requestId,
            @RequestParam FactoryRequestStatus status
    ) {
        return factoryRequestService.updateStatus(requestId, status);
    }
    // PATCH /api/v1/factory-requests/{id}/receive
    @PatchMapping("/{id}/receive")
    @Operation(summary = "Receive products from factory (partial delivery)")
    public FactoryRequest receive(
            @PathVariable("id") Long requestId,
            @RequestParam int quantity
    ) {
        return factoryRequestService.receive(requestId, quantity);
    }
}
