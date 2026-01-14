package org.hanshul.service;

import org.hanshul.entities.ParkingFloor;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int parkingFloorsCount;
    private List<ParkingFloor> parkingFloors;
    private static volatile ParkingLot INSTANCE;
    private ParkingLot() {
        parkingFloors = new ArrayList<>();
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

    public void addFloor(int newFloorNumber) {
        parkingFloors.add(new ParkingFloor(newFloorNumber,new ArrayList<>()));
    }

    public int getAvailableFloorNumbers() {
        return parkingFloorsCount;
    }

}
