// 名前: Tram, Uyen
package com.ra.bakerysystem.common;

// FactoryRequestStatus đại diện cho các trạng thái có thể có của một yêu cầu gửi từ hệ thống tới xưởng sản xuất
public enum FactoryRequestStatus {
    PENDING,     // Yêu cầu đã được tạo nhưng chưa được xử lý hoặc giao hàng
    DELIVERED,   // Yêu cầu đã được xưởng xử lý và giao hàng thành công
    CANCELLED    // Yêu cầu đã bị hủy (do khách hàng hoặc hệ thống)
}
