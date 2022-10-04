package com.navi.assignment;

import com.navi.assignment.models.*;
import com.navi.assignment.repository.BookingRepository;
import com.navi.assignment.repository.BranchRepository;
import com.navi.assignment.repository.VehicleRepository;
import com.navi.assignment.service.BookingService;
import com.navi.assignment.service.BranchService;
import com.navi.assignment.service.VehicleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class InputProcessor {

    private final BranchService branchService;
    private final VehicleService vehicleService;
    private final BookingService bookingService;

    public InputProcessor() {
        BranchRepository branchRepository = new BranchRepository();
        VehicleRepository vehicleRepository = new VehicleRepository();
        BookingRepository bookingRepository = new BookingRepository();
        branchService = new BranchService(branchRepository);
        vehicleService = new VehicleService(branchRepository, vehicleRepository);
        bookingService = new BookingService(bookingRepository, vehicleRepository);
    }

    public void process(BufferedReader reader) throws IOException {
        String st;
        while ((st = reader.readLine()) != null) {
            String[] command = st.split(" ");
            switch (command[0]) {
                case "ADD_BRANCH":
                    addBranch(command);
                    break;
                case "ADD_VEHICLE":
                    addVehicle(command);
                    break;
                case "BOOK":
                    book(command);
                    break;
                case "DISPLAY_VEHICLES":
                    displayVehicles(command);
                    break;
            }
        }
    }

    private void displayVehicles(String[] command) {
        String branchId = command[1];
        int start = Integer.parseInt(command[2]);
        int end = Integer.parseInt(command[3]);
        List<Vehicle> vehicles = bookingService.displayVehicles(branchId, new Slot(start, end));
        StringBuilder sb = new StringBuilder();
        for (Vehicle vehicle : vehicles) {
            sb.append(vehicle.getVehicleId())
                    .append(" ");
        }
        System.out.println(sb.toString().trim());
    }

    private void book(String[] command) {
        String branchId = command[1];
        VehicleType vehicleType = VehicleType.valueOf(command[2]);
        int start = Integer.parseInt(command[3]);
        int end = Integer.parseInt(command[4]);
        int price = (int) bookingService.book(new Booking(branchId, vehicleType, new Slot(start, end)));
        System.out.println(price);
    }

    private void addVehicle(String[] command) {
        String branchId = command[1];
        VehicleType vehicleType = VehicleType.valueOf(command[2]);
        String vehicleId = command[3];
        double price = Double.parseDouble(command[4]);
        boolean added = vehicleService.addVehicle(new Vehicle(branchId, vehicleId, vehicleType, price));
        System.out.println(String.valueOf(added).toUpperCase());
    }

    private void addBranch(String[] command) {
        String branchId = command[1];
        String[] vehicleTypeStrings = command[2].split(",");
        HashSet<VehicleType> vehicleTypes = new HashSet<>();
        for (String vehicleType : vehicleTypeStrings) {
            vehicleTypes.add(VehicleType.valueOf(vehicleType));
        }
        boolean b = branchService.addBranch(new Branch(branchId, vehicleTypes));
        System.out.println(String.valueOf(b).toUpperCase());
    }
}
