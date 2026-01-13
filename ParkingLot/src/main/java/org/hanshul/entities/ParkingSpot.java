package org.hanshul.entities;

import lombok.Getter;
import lombok.Setter;
import org.hanshul.entities.vehicle.Vehicle;
import org.hanshul.enums.VehicleSize;

@Getter
@Setter
public class ParkingSpot {
    private VehicleSize spotSize;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSpot(VehicleSize spotSize) {
        this.spotSize = spotSize;
    }
}
