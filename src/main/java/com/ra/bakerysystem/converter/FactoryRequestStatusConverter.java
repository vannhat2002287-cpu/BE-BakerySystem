package com.ra.bakerysystem.converter;

import com.ra.bakerysystem.common.FactoryRequestStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class FactoryRequestStatusConverter
        implements AttributeConverter<FactoryRequestStatus, String> {

    @Override
    public String convertToDatabaseColumn(FactoryRequestStatus status) {
        if (status == null) return null;

        return switch (status) {
            case PENDING -> "CREATED";
            case PARTIAL -> "PARTIALLY_DELIVERED";
            case DELIVERED -> "DELIVERED";
            case CANCELLED -> "CANCELLED";
        };
    }

    @Override
    public FactoryRequestStatus convertToEntityAttribute(String dbValue) {
        if (dbValue == null) return null;

        return switch (dbValue) {
            case "CREATED" -> FactoryRequestStatus.PENDING;
            case "PARTIALLY_DELIVERED" -> FactoryRequestStatus.PARTIAL;
            case "DELIVERED" -> FactoryRequestStatus.DELIVERED;
            case "CANCELLED" -> FactoryRequestStatus.CANCELLED;
            default -> throw new IllegalArgumentException(
                    "Unknown status from DB: " + dbValue
            );
        };
    }
}
