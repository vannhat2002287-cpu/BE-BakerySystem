package com.ra.bakerysystem.repository;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import com.ra.bakerysystem.model.entity.FactoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
/**
 * Repository thao tác với bảng factory_requests
 *
 * - Chịu trách nhiệm truy vấn / kiểm tra dữ liệu FactoryRequest trong DB
 * - Kế thừa JpaRepository để sử dụng sẵn các CRUD method:
 *   save(), findById(), findAll(), deleteById(), ...
 */
public interface FactoryRequestRepository extends JpaRepository<FactoryRequest, Long> {
    /**
     * Kiểm tra xem một sản phẩm trong một ngày kinh doanh
     * đã tồn tại FactoryRequest ở các trạng thái chỉ định hay chưa.
     *
     * Thường dùng để:
     * - Ngăn tạo trùng factory request
     * - Đảm bảo mỗi sản phẩm chỉ có 1 request đang xử lý trong 1 ngày
     *
     * Spring Data JPA sẽ tự sinh SQL tương đương:
     *
     * SELECT COUNT(*) > 0
     * FROM factory_requests
     * WHERE product_id = ?
     *   AND business_date = ?
     *   AND status IN (?, ?, ...);
     *
     * @param productId    ID của sản phẩm
     * @param businessDate Ngày kinh doanh (DATE)
     * @param statuses     Danh sách trạng thái cần kiểm tra
     * @return true nếu đã tồn tại request thỏa điều kiện, ngược lại false
     */
    boolean existsByProductIdAndBusinessDateAndStatusIn(
            Long productId,
            LocalDate businessDate,
            List<FactoryRequestStatus> statuses
    );
}

