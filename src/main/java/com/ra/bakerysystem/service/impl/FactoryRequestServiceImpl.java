package com.ra.bakerysystem.service.impl;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import com.ra.bakerysystem.model.DTO.FactoryRequestDTO;
import com.ra.bakerysystem.model.entity.FactoryRequest;
import com.ra.bakerysystem.model.entity.Inventory;
import com.ra.bakerysystem.model.entity.Product;
import com.ra.bakerysystem.repository.FactoryRequestRepository;
import com.ra.bakerysystem.repository.InventoryRepository;
import com.ra.bakerysystem.repository.ProductRepository;
import com.ra.bakerysystem.service.FactoryRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FactoryRequestServiceImpl implements FactoryRequestService {

    private final FactoryRequestRepository factoryRequestRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final ProductionCalculationService productionCalculationService;


    // RECEIVE (NHẬP KHO THEO GIỜ)
    @Override
    public FactoryRequest receive(Long requestId, int quantity) {

        FactoryRequest request = factoryRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Factory request not found"));

        Inventory inventory = inventoryRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

        int delivered = request.getDeliveredQuantity() == null
                ? 0
                : request.getDeliveredQuantity();

        request.setDeliveredQuantity(delivered + quantity);

        inventory.setCurrentQuantity(
                inventory.getCurrentQuantity() + quantity
        );
        // Thêm lastUpdated khi nhập kho
        inventory.setLastUpdated(LocalDateTime.now());

        inventoryRepository.save(inventory);

        if (request.getDeliveredQuantity() == 0) {
            request.setStatus(FactoryRequestStatus.PENDING);
        } else if (request.getDeliveredQuantity() < request.getRequestQuantity()) {
            request.setStatus(FactoryRequestStatus.PARTIAL);
        } else {
            request.setStatus(FactoryRequestStatus.DELIVERED);
        }
        return factoryRequestRepository.save(request);
    }

    // ===================== GET ALL =====================
    @Override
    public List<FactoryRequest> getAll() {
        return factoryRequestRepository.findAll();
    }

    // ===================== UPDATE STATUS =====================
    /**
     * Cập nhật trạng thái của Factory Request.
     *
     * Lưu ý:
     * - Method này CHỈ cập nhật trạng thái.
     * - KHÔNG xử lý nhập kho.
     * - Nhập kho được thực hiện thông qua method receive().
     */
    @Override
    public FactoryRequest updateStatus(Long requestId, FactoryRequestStatus status) {

        FactoryRequest request = factoryRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Factory request not found"));

        request.setStatus(status);
        return factoryRequestRepository.save(request);
    }

    // AUTO CREATE (12:00 / 17:00)
    @Override
    public void autoCreateFactoryRequests() {

        LocalDate today = LocalDate.now();
        List<Product> products = productRepository.findAll();

        for (Product product : products) {

            Inventory inventory = inventoryRepository.findById(product.getId())
                    .orElse(null);
            if (inventory == null) continue;

            if (inventory.getCurrentQuantity() >= inventory.getReorderPoint()) {
                continue;
            }

            boolean hasPending =
                    factoryRequestRepository
                            .existsByProductIdAndBusinessDateAndStatusIn(
                                    product.getId(),
                                    today,
                                    List.of(
                                            FactoryRequestStatus.PENDING,
                                            FactoryRequestStatus.PARTIAL
                                    )
                            );

            if (hasPending) continue;

            int qty = productionCalculationService.calculateProductionQuantity(
                    product.getId(),
                    today
            );

            FactoryRequest request = FactoryRequest.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .businessDate(today)
                    .requestQuantity(Math.max(qty, 10)) // QA rule: min 10
                    .deliveredQuantity(0)
                    .status(FactoryRequestStatus.PENDING)
                    .createdAt(LocalDateTime.now())
                    .etaAt(LocalDateTime.now().plusDays(1)) // ETA mặc định cho request auto / khi FE không truyền
                    .build();
            factoryRequestRepository.save(request);
        }
    }

    //  CREATE (MANUAL – GIỮ LẠI)
    @Override
    public FactoryRequest create(FactoryRequestDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        FactoryRequest request = FactoryRequest.builder()
                .productId(product.getId())
                .productName(product.getName())
                .requestQuantity(dto.getRequestQuantity())
                .deliveredQuantity(0)
                .businessDate(LocalDate.now())
                .etaAt(dto.getEtaAt())
                .note(dto.getNote())
                .status(FactoryRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return factoryRequestRepository.save(request);
    }
}
