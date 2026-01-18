package org.hanshul.service;

import org.hanshul.entities.ParkingFloor;
import org.hanshul.entities.ParkingSpot;
import org.hanshul.entities.Ticket;
import org.hanshul.entities.vehicle.Vehicle;
import org.hanshul.enums.VehicleSize;
import org.hanshul.strategy.fee.FeeCalculationStrategy;
import org.hanshul.strategy.fee.FlatFeeCalculation;
import org.hanshul.strategy.fee.VehicleBasedFeeCalculation;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParkingLot {
    private int parkingFloorsCount;
    private List<ParkingFloor> parkingFloors;
    private static volatile ParkingLot INSTANCE;
    private Map<String, Ticket> allActiveTickets;
    private FeeCalculationStrategy feeCalculationStrategy;

    private ParkingLot() {
        parkingFloors = new ArrayList<>();
        allActiveTickets = new HashMap<>();
    }

    public ParkingLot getInstance() {
        // DCL - Double checked locking using result variable.
        ParkingLot result = INSTANCE;
        if (result != null) {
            return result;
        }
        synchronized (ParkingLot.class) {
            if (INSTANCE == null) {
                INSTANCE = new ParkingLot();
            }
            return INSTANCE;
        }
    }

    public void addFloor(List<ParkingSpot> parkingSpots) {
        parkingFloors.add(new ParkingFloor(parkingFloors.size() + 1, parkingSpots));
        parkingFloorsCount = parkingFloors.size();
    }

    public void addParkingSpotsToExistingFloor(int floorNumber, List<ParkingSpot> parkingSpots) {
        if ((floorNumber - 1) < parkingFloors.size()) {
            parkingFloors.get(floorNumber - 1).getSpots().addAll(parkingSpots);
        }
    }

    public void addParkingSpotToExistingFloor(int floorNumber, ParkingSpot parkingSpot) {
        if ((floorNumber - 1) < parkingFloors.size()) {
            parkingFloors.get(floorNumber - 1).getSpots().add(parkingSpot);
        }
    }

    public boolean removeParkingSpot(int floorNumber, ParkingSpot parkingSpot) {
        if ((floorNumber - 1) < parkingFloors.size()) {
            return parkingFloors.get(floorNumber - 1).getSpots().remove(parkingSpot);
        }
        return false;
    }

    public ParkingSpot createParkingSpot(VehicleSize vehicleSize) {
        return new ParkingSpot(vehicleSize);
    }

    public Optional<Ticket> parkVehicle(Vehicle vehicle) {
        Optional<Ticket> ticket = Optional.empty();
        for (ParkingFloor floor : parkingFloors) {
            List<ParkingSpot> individualParkingSpots = floor.getSpots();
            for (ParkingSpot spot : individualParkingSpots) {
                if (!spot.isOccupied() && spot.getSpotSize().equals(vehicle.getVehicleSize())) {
                    spot.setOccupied(true);
                    ticket = Optional.of(new Ticket(vehicle));
                    ticket.ifPresent(activeTicket -> allActiveTickets.put(activeTicket.getTicketId(), activeTicket));
                }
            }
        }
        return ticket;
    }

    public double unparkVehicle(String ticketId) {
        double parkingCost = 0.0;
        if (allActiveTickets.containsKey(ticketId)) {
            Ticket ticket = allActiveTickets.get(ticketId);
            ticket.markExit();
            Instant entryTime = ticket.getEntryTime(), exitTime = ticket.getExitTime();
            DayOfWeek dayOfWeek = entryTime.atZone(ZoneId.systemDefault()).getDayOfWeek();
            Duration parkedDuration = Duration.between(entryTime, exitTime);
            VehicleSize vehicleSize = ticket.getVehicleSize();
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                feeCalculationStrategy = new VehicleBasedFeeCalculation();
                parkingCost = feeCalculationStrategy.calculate(parkedDuration, vehicleSize);
            } else {
                feeCalculationStrategy = new FlatFeeCalculation();
                parkingCost = feeCalculationStrategy.calculate(parkedDuration, vehicleSize);
            }
        }
        return parkingCost;
    }

    public int getAvailableFloorNumbers() {
        return parkingFloorsCount;
    }

    public String ticketDetails(String ticketId) {
        if (allActiveTickets.containsKey(ticketId)) {
            return allActiveTickets.get(ticketId).toString();
        } else {
            return "Invalid ticketId no ticket with such ticketId exists";
        }
    }

}
