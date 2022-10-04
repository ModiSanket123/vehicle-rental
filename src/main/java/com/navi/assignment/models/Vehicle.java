package com.navi.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = "price")
public class Vehicle {
    private String branchId;
    private String vehicleId;
    private VehicleType vehicleType;
    private double price;

    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class VehicleUniqueConstraint {
        private String branchId;
        private String vehicleId;
    }
}
