package com.navi.assignment.service;

import com.navi.assignment.models.Branch;
import com.navi.assignment.models.VehicleType;
import com.navi.assignment.repository.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class BranchServiceTest {

    private BranchService branchService;

    @BeforeEach
    void setUp() {
        BranchRepository branchRepository = new BranchRepository();
        branchService = new BranchService(branchRepository);
    }

    @Test
    void addBranchIsSuccessWhenNoDuplicate() {
        Branch branchB1 = new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE)));
        Branch branchB2 = new Branch("B2", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE, VehicleType.BUS)));

        boolean successB1 = branchService.addBranch(branchB1);
        boolean successB2 = branchService.addBranch(branchB2);

        assertTrue(successB1);
        assertTrue(successB2);
    }

    @Test
    void testAddBranchIsFalseWhenDuplicate() {
        Branch branchB1 = new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE)));
        Branch branchB1Dup = new Branch("B1", new HashSet<>(Arrays.asList(VehicleType.CAR, VehicleType.BIKE, VehicleType.BUS)));

        boolean successB1 = branchService.addBranch(branchB1);
        boolean successB1Dup = branchService.addBranch(branchB1Dup);

        assertTrue(successB1);
        assertFalse(successB1Dup);
    }
}