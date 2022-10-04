package com.navi.assignment.repository;

import com.navi.assignment.models.Booking;
import com.navi.assignment.models.Booking.BookingCompositeKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingRepository extends Repository<BookingCompositeKey, Booking>{
    private final HashMap<String, Map<String, List<Booking>>> groupByBranchVehicle;

    public BookingRepository() {
        super();
        groupByBranchVehicle = new HashMap<>();
    }

    @Override
    public boolean save(Booking booking) {
        boolean success = super.save(booking);
        if(success) {
            Map<String, List<Booking>> groupedByVehicle = groupByBranchVehicle.computeIfAbsent(booking.getBranchId(), (k) -> new HashMap<>());
            List<Booking> bookings = groupedByVehicle.computeIfAbsent(booking.getVehicleId(), (k) -> new ArrayList<>());
            bookings.add(booking);
        }
        return success;
    }

    @Override
    public BookingCompositeKey getKey(Booking booking) {
        return new BookingCompositeKey(booking.getBranchId(),booking.getVehicleId(), booking.getSlot());
    }

    public Map<String, List<Booking>> getGroupedByVehicle(String branchId) {
        return groupByBranchVehicle.getOrDefault(branchId, new HashMap<>());
    }

    public List<Booking> getBookings(String branchId, String vehicleId) {
        return getGroupedByVehicle(branchId).getOrDefault(vehicleId, new ArrayList<>());
    }
}
