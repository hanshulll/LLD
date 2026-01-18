package org.hanshul.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hanshul.entities.vehicle.Vehicle;
import org.hanshul.enums.VehicleSize;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    private String ticketId;
    private VehicleSize vehicleSize;
    private Vehicle vehicle;
    private Instant entryTime;
    private Instant exitTime;

    public Ticket(Vehicle vehicle) {
        this.ticketId = UUID.randomUUID().toString();
        this.vehicle = vehicle;
        this.vehicleSize = vehicle.getVehicleSize();
        this.entryTime = Instant.now();
    }

    public void markExit() {
        this.exitTime = Instant.now();
    }
}
