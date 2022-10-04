package com.navi.assignment.service;

import com.navi.assignment.models.Booking;
import com.navi.assignment.models.Slot;
import com.navi.assignment.models.Vehicle;
import com.navi.assignment.repository.BookingRepository;
import com.navi.assignment.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

public class BookingService {
    public final BookingRepository bookingRepository;
    public final VehicleRepository vehicleRepository;

    public BookingService(BookingRepository bookingRepository, VehicleRepository vehicleRepository) {
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public double book(Booking booking) {
        List<Vehicle> vehicles = vehicleRepository.getVehicles(booking.getBranchId(), booking.getVehicleType());
        for (Vehicle vehicle : vehicles) {
            List<Booking> vehicleBookings = bookingRepository.getBookings(booking.getBranchId(), vehicle.getVehicleId());
            if (slotAvailable(booking.getSlot(), vehicleBookings)) {
                booking.setVehicleId(vehicle.getVehicleId());
                booking.setBookingPrice(getBookingCost(booking, vehicle));
                bookingRepository.save(booking);
                return booking.getBookingPrice();
            }
        }
        return -1;
    }

    public List<Vehicle> displayVehicles(String branchId, Slot slot) {
        List<Vehicle> availableVehicles = new ArrayList<>();
        List<Vehicle> allVehicles = vehicleRepository.getVehicleSortedOnPrice(branchId);
        for (Vehicle vehicle : allVehicles) {
            List<Booking> bookings = bookingRepository.getBookings(branchId, vehicle.getVehicleId());
            if (slotAvailable(slot, bookings)) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    private static double getBookingCost(Booking booking, Vehicle vehicle) {
        double pricePerHour = vehicle.getPrice();
        Slot slot = booking.getSlot();
        return pricePerHour * (slot.getEnd() - slot.getStart());
    }

    private boolean slotAvailable(Slot slot, List<Booking> bookings) {
        for (Booking acceptedBooking : bookings) {
            if (slot.overlaps(acceptedBooking.getSlot())) {
                return false;
            }
        }
        return true;
    }

}
