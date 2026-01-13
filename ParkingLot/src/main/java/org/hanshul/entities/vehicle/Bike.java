package org.hanshul.entities.vehicle;

import org.hanshul.enums.VehicleSize;


public class Bike extends Vehicle {
    private final String licencePlate;
    private final VehicleSize vehicleSize;
    public Bike(String licencePlate, VehicleSize vehicleSize) {
        this.licencePlate=licencePlate;
        this.vehicleSize=vehicleSize;
    }

    @Override
    public String getLicencePlate() {
        return licencePlate;
    }

    @Override
    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }
}
