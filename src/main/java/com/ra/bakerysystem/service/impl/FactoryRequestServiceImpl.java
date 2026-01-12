// 名前: Tram, Nhat
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FactoryRequestServiceImpl implements FactoryRequestService {

    private final FactoryRequestRepository factoryRequestRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public FactoryRequest create(FactoryRequestDTO dto) {

        // Lấy thông tin product
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Tạo mới FactoryRequest entity
        FactoryRequest request = FactoryRequest.builder()
                .productId(product.getId())
                .productName(product.getName())
                .requestQuantity(dto.getRequestQuantity())
                .etaAt(dto.getEtaAt())
                .note(dto.getNote())
                .status(FactoryRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        // Lưu factory request vào database
        return factoryRequestRepository.save(request);
    }

    @Override
    public List<FactoryRequest> getAll() {
        return factoryRequestRepository.findAll();
    }


    /**
     * Cập nhật trạng thái của Factory Request
     *  - Khi trạng thái được cập nhật sang DELIVERED
     *  - Và trạng thái trước đó KHÔNG phải DELIVERED
     *  => hệ thống sẽ tự động cộng số lượng vào Inventory
     */
    @Override
    public FactoryRequest updateStatus(Long requestId, FactoryRequestStatus status) {
        // Lấy factory request theo id
        FactoryRequest request = factoryRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Factory request not found"));

        // // Logic khi nhận hàng từ nhà máy
        if (status == FactoryRequestStatus.DELIVERED
                && request.getStatus() != FactoryRequestStatus.DELIVERED) {

            // Lấy inventory tương ứng với product
            Inventory inventory = inventoryRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Inventory not found"));

            // Cộng thêm số lượng vào tồn kho
            inventory.setCurrentQuantity(
                    inventory.getCurrentQuantity() + request.getRequestQuantity()
            );

            // Lưu lại inventory
            inventoryRepository.save(inventory);
        }

        // Cập nhật trạng thái request
        request.setStatus(status);
        // Lưu lại factory request
        return factoryRequestRepository.save(request);
    }
}
