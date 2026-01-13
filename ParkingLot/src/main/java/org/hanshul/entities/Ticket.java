package org.hanshul.entities;

import lombok.Getter;
import lombok.Setter;
import org.hanshul.entities.vehicle.Vehicle;
import org.hanshul.enums.VehicleSize;

import java.time.Instant;

@Getter
@Setter
public class Ticket {
    private String ticketId;
    private VehicleSize vehicleSize;
    private Vehicle vehicle;
    private Instant entryTime;
    private Instant exitTime;

    public Ticket(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.vehicleSize = vehicle.getVehicleSize();
        this.entryTime = Instant.now();
    }
}
