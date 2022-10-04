package com.navi.assignment.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "bookingPrice")
public class Booking {
    private String branchId;
    private String vehicleId;
    private double bookingPrice;
    private VehicleType vehicleType;
    private Slot slot;

    public Booking(String branchId, VehicleType vehicleType, Slot slot) {
        this(branchId, null, 0, vehicleType, slot);
    }

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class BookingCompositeKey{
        private String branchId;
        private String vehicleId;
        private Slot slot;
    }

}
