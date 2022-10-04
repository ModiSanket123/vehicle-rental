package com.navi.assignment.repository;

import com.navi.assignment.models.Vehicle;
import com.navi.assignment.models.Vehicle.VehicleUniqueConstraint;
import com.navi.assignment.models.VehicleType;

import java.util.*;
import java.util.stream.Collectors;

public class VehicleRepository extends Repository<VehicleUniqueConstraint, Vehicle> {
    private final Map<String, Map<VehicleType, List<Vehicle>>> groupedByBranchAndVehicleType;

    public VehicleRepository() {
        super();
        this.groupedByBranchAndVehicleType = new HashMap<>();
    }

    @Override
    public boolean save(Vehicle vehicle) {
        boolean success = super.save(vehicle);
        if (success) {
            Map<VehicleType, List<Vehicle>> groupedByType = groupedByBranchAndVehicleType.computeIfAbsent(vehicle.getBranchId(), (k) -> new HashMap<>());
            List<Vehicle> vehicles = groupedByType.computeIfAbsent(vehicle.getVehicleType(), (k) -> new ArrayList<>());
            vehicles.add(vehicle);
        }
        return success;
    }

    @Override
    public VehicleUniqueConstraint getKey(Vehicle vehicle) {
        return new VehicleUniqueConstraint(vehicle.getBranchId(), vehicle.getVehicleId());
    }

    public Map<VehicleType, List<Vehicle>> getVehicleGroupedByType(String branchId) {
        return groupedByBranchAndVehicleType.getOrDefault(branchId, new HashMap<>());
    }

    public List<Vehicle> getVehicles(String branchId, VehicleType vehicleType) {
        return getVehicleGroupedByType(branchId).getOrDefault(vehicleType, new ArrayList<>());
    }

    public List<Vehicle> getVehicleSortedOnPrice(String branchId) {
        return getVehicleGroupedByType(branchId).values().stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingDouble(Vehicle::getPrice))
                .collect(Collectors.toList());
    }
}
