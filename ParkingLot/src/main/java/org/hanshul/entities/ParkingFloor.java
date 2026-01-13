package org.hanshul.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class ParkingFloor {
    private int floorNumber;
    private List<ParkingSpot> spots;

    public ParkingFloor(int floorNumber,List<ParkingSpot> spots) {
        this.floorNumber = floorNumber;
        this.spots = spots;
    }

}
