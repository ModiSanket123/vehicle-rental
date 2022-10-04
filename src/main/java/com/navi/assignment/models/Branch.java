package com.navi.assignment.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode(exclude = "vehicleTypes")
public class Branch {
    private String branchId;
    private Set<VehicleType> vehicleTypes;


}
