package com.navi.assignment.service;

import com.navi.assignment.models.*;
import com.navi.assignment.repository.BookingRepository;
import com.navi.assignment.repository.BranchRepository;
import com.navi.assignment.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private BookingService bookingService;
    private BranchService branchService;
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        VehicleRepository vehicleRepository = new VehicleRepository();
        BookingRepository bookingRepository = new BookingRepository();
        BranchRepository branchRepository = new BranchRepository();
        branchService = new BranchService(branchRepository);
        vehicleService = new VehicleService(branchRepository, vehicleRepository);
        bookingService = new BookingService(bookingRepository, vehicleRepository);
    }

    @Test
    void testBookWhenNoneAvailable() {
        double book = bookingService.book(new Booking("B1", VehicleType.BIKE, new Slot(1, 2)));

        assertEquals(-1, book);
    }

    @Test
    void testBookWhenAllAvailable() {
        branchService.addBranch(new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE))));
        vehicleService.addVehicle(new Vehicle("B1", "V1", VehicleType.CAR, 100));
        vehicleService.addVehicle(new Vehicle("B1", "V2", VehicleType.BIKE, 50));

        double booking1 = bookingService.book(new Booking("B1", VehicleType.BIKE, new Slot(3, 7)));
        double booking2 = bookingService.book(new Booking("B1", VehicleType.CAR, new Slot(1, 6)));

        assertEquals(200, booking1);
        assertEquals(500, booking2);
    }

    @Test
    void testBookWhenSlotOverlaps() {
        branchService.addBranch(new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE))));
        vehicleService.addVehicle(new Vehicle("B1", "V1", VehicleType.CAR, 100));
        vehicleService.addVehicle(new Vehicle("B1", "V2", VehicleType.BIKE, 50));

        double booking1 = bookingService.book(new Booking("B1", VehicleType.BIKE, new Slot(3, 7)));
        double booking2 = bookingService.book(new Booking("B1", VehicleType.CAR, new Slot(1, 6)));
        double booking3 = bookingService.book(new Booking("B1", VehicleType.BIKE, new Slot(2, 6)));
        double booking4 = bookingService.book(new Booking("B1", VehicleType.CAR, new Slot(5, 10)));

        assertEquals(200, booking1);
        assertEquals(500, booking2);
        assertEquals(-1, booking3);
        assertEquals(-1, booking4);
    }

    @Test
    void testListAvailableVehiclesWhenNoneAvailable() {
        List<Vehicle> vehicles = bookingService.displayVehicles("B1", new Slot(1, 2));

        assertTrue(vehicles.isEmpty());
    }

    @Test
    void testListAvailableVehicles() {
        branchService.addBranch(new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE))));
        Vehicle vehicle1 = new Vehicle("B1", "V1", VehicleType.CAR, 100);
        Vehicle vehicle2 = new Vehicle("B1", "V2", VehicleType.BIKE, 50);
        vehicleService.addVehicle(vehicle1);
        vehicleService.addVehicle(vehicle2);
        bookingService.book(new Booking("B1", VehicleType.CAR, new Slot(10, 12)));
        bookingService.book(new Booking("B1", VehicleType.BIKE, new Slot(3, 7)));

        List<Vehicle> allVehiclesAvailable = bookingService.displayVehicles("B1", new Slot(1, 2));
        List<Vehicle> oneAvailable = bookingService.displayVehicles("B1", new Slot(2, 8));
        List<Vehicle> noneAvailable = bookingService.displayVehicles("B1", new Slot(5, 11));

        assertEquals(Collections.emptyList(), noneAvailable);
        assertEquals(Collections.singletonList(vehicle1), oneAvailable);
        assertEquals(Arrays.asList(vehicle2, vehicle1), allVehiclesAvailable);
    }

    @Test
    void testListAvailableVehiclesAreSorted() {
        branchService.addBranch(new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE, VehicleType.BUS, VehicleType.VAN))));
        Vehicle vehicle1 = new Vehicle("B1", "V1", VehicleType.CAR, 100);
        Vehicle vehicle2 = new Vehicle("B1", "V2", VehicleType.BIKE, 50);
        Vehicle vehicle3 = new Vehicle("B1", "V3", VehicleType.BUS, 200);
        Vehicle vehicle4 = new Vehicle("B1", "V4", VehicleType.VAN, 150);
        vehicleService.addVehicle(vehicle1);
        vehicleService.addVehicle(vehicle2);
        vehicleService.addVehicle(vehicle3);
        vehicleService.addVehicle(vehicle4);

        List<Vehicle> allVehiclesAvailable = bookingService.displayVehicles("B1", new Slot(1, 2));

        assertEquals(Arrays.asList(vehicle2, vehicle1, vehicle4, vehicle3), allVehiclesAvailable);
    }


}