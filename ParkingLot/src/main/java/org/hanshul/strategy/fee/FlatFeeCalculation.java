package org.hanshul.strategy.fee;

import org.hanshul.enums.VehicleSize;
import org.hanshul.strategy.fee.FeeCalculationStrategy;

import java.time.Duration;
import java.time.Instant;

public class FlatFeeCalculation implements FeeCalculationStrategy {
    private static final double parkingRate;

    static {
        parkingRate = 8.0;
    }

    @Override
    public double calculate(Duration duration, VehicleSize vehicleSize) {
        long hours = duration.toHours();
        if (duration.toMinutes()!=60) {
            hours++;
        }
        return hours*parkingRate;
    }
}
