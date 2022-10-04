package com.navi.assignment.service;

import com.navi.assignment.models.Branch;
import com.navi.assignment.models.Vehicle;
import com.navi.assignment.models.VehicleType;
import com.navi.assignment.repository.BranchRepository;
import com.navi.assignment.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleServiceTest {

    private VehicleService vehicleService;
    private BranchService branchService;

    @BeforeEach
    void setUp() {
        BranchRepository branchRepository = new BranchRepository();
        branchService = new BranchService(branchRepository);
        vehicleService = new VehicleService(branchRepository, new VehicleRepository());
    }

    @Test
    void testAddVehicleIsTrueWhenDuplicateDoesntExist() {
        Branch branchB1 = new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE)));
        Vehicle vehicleV1 = new Vehicle("B1", "V1", VehicleType.CAR, 100);
        branchService.addBranch(branchB1);

        boolean success = vehicleService.addVehicle(vehicleV1);

        assertTrue(success);
    }

    @Test
    void testAddVehicleIsFalseWhenDuplicateIdExist() {
        Branch branchB1 = new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE)));
        Vehicle vehicleV1 = new Vehicle("B1", "V1", VehicleType.CAR, 100);
        Vehicle vehicleV1Dup = new Vehicle("B1", "V1", VehicleType.BIKE, 50);
        branchService.addBranch(branchB1);

        boolean successV1 = vehicleService.addVehicle(vehicleV1);
        boolean successV1Dup = vehicleService.addVehicle(vehicleV1Dup);

        assertTrue(successV1);
        assertFalse(successV1Dup);
    }

    @Test
    void testAddVehicleIsFalseWhenBranchDoesntExist() {
        Vehicle vehicle = new Vehicle("B1", "V1", VehicleType.CAR, 100);

        boolean success = vehicleService.addVehicle(vehicle);

        assertFalse(success);
    }
}