package com.navi.assignment.service;

import com.navi.assignment.models.Branch;
import com.navi.assignment.models.Vehicle;
import com.navi.assignment.repository.BranchRepository;
import com.navi.assignment.repository.VehicleRepository;

public class VehicleService {
    private final BranchRepository branchRepository;
    public final VehicleRepository vehicleRepository;

    public VehicleService(BranchRepository branchRepository, VehicleRepository vehicleRepository) {
        this.branchRepository = branchRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public boolean addVehicle(Vehicle vehicle){
        if(vehicleSupported(vehicle) && !vehicleRepository.containsKey(vehicleRepository.getKey(vehicle))){
            return vehicleRepository.save(vehicle);
        }else {
            return false;
        }
    }

    private boolean vehicleSupported(Vehicle vehicle) {
        Branch branch = branchRepository.get(vehicle.getBranchId());
        return branch != null && branch.getVehicleTypes().contains(vehicle.getVehicleType());
    }
}
