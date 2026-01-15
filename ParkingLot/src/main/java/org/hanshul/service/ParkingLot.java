package org.hanshul.service;

import org.hanshul.entities.ParkingFloor;
import org.hanshul.entities.ParkingSpot;
import org.hanshul.entities.Ticket;
import org.hanshul.entities.vehicle.Vehicle;
import org.hanshul.enums.VehicleSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParkingLot {
    private int parkingFloorsCount;
    private List<ParkingFloor> parkingFloors;
    private static volatile ParkingLot INSTANCE;

    private Map<String,Ticket> allActiveTickets;
    private ParkingLot() {
        parkingFloors = new ArrayList<>();
        allActiveTickets = new HashMap<>();
    }

    public ParkingLot getInstance() {
        // DCL - Double checked locking using result variable.
        ParkingLot result = INSTANCE;
        if (result!=null) {
            return result;
        }
        synchronized (ParkingLot.class) {
            if (INSTANCE==null) {
                INSTANCE = new ParkingLot();
            }
            return INSTANCE;
        }
    }

    public void addFloor(List<ParkingSpot> parkingSpots) {
        parkingFloors.add(new ParkingFloor(parkingFloors.size()+1,parkingSpots));
        parkingFloorsCount = parkingFloors.size();
    }

    public void addParkingSpotsToExistingFloor(int floorNumber, List<ParkingSpot> parkingSpots) {
        if (floorNumber<parkingFloors.size()) {
            parkingFloors.get(floorNumber).getSpots().addAll(parkingSpots);
        }
    }

    public void addParkingSpotToExistingFloor(int floorNumber, ParkingSpot parkingSpot) {
        if (floorNumber<parkingFloors.size()) {
            parkingFloors.get(floorNumber).getSpots().add(parkingSpot);
        }
    }

    public boolean removeParkingSpot(int floorNumber, ParkingSpot parkingSpot) {
        return false;
    }

    public ParkingSpot createParkingSpot(VehicleSize vehicleSize) {
        return new ParkingSpot(vehicleSize);
    }

    public Optional<Ticket> parkVehicle(Vehicle vehicle) {
        Optional<Ticket> ticket = Optional.empty();
        for (ParkingFloor floor : parkingFloors) {
            List<ParkingSpot> individualParkingSpots = floor.getSpots();
            for(ParkingSpot spot : individualParkingSpots) {
                if (!spot.isOccupied() && spot.getSpotSize().equals(vehicle.getVehicleSize())) {
                    spot.setOccupied(true);
                    ticket = Optional.of(new Ticket(vehicle));
                    ticket.ifPresent(activeTicket->allActiveTickets.put(activeTicket.getTicketId(),activeTicket));
                }
            }
        }
        return ticket;
    }

    public double unparkVehicle(String ticketId) {
        return 0.0;
    }

    public int getAvailableFloorNumbers() {
        return parkingFloorsCount;
    }

}
