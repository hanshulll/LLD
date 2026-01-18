package org.hanshul.strategy.fee;

import org.hanshul.enums.VehicleSize;
import org.hanshul.strategy.fee.FeeCalculationStrategy;

import java.time.Duration;
import java.util.Map;

public class VehicleBasedFeeCalculation implements FeeCalculationStrategy {
    @Override
    public double calculate(Duration duration, VehicleSize vehicleSize) {
        double parkingRates = getHourlyRates(vehicleSize);
        long hours = duration.toHours();
        if (duration.toMinutes()!=60) {
            hours++;
        }
        return parkingRates*hours;
    }

    public double getHourlyRates(VehicleSize vehicleSize) {
        switch (vehicleSize) {
            case LARGE: return 15.1;
            case MEDIUM: return 10.9;
            case SMALL: return 5.5;
            default: return 5;
        }
    }
}
