package org.hanshul.strategy.fee;

import org.hanshul.enums.VehicleSize;

import java.time.Duration;

public interface FeeCalculationStrategy {
        double calculate(Duration duration, VehicleSize vehicleSize);
}
