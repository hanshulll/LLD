package org.hanshul.strategy.fee;

import org.hanshul.enums.VehicleSize;
import org.hanshul.strategy.fee.FeeCalculationStrategy;

import java.util.Map;

public class VehicleBasedFeeCalculation implements FeeCalculationStrategy {
    private Map<VehicleSize,Double> parkingRates = Map.of(VehicleSize.LARGE,15.1,VehicleSize.MEDIUM,10.9,VehicleSize.SMALL,5.5);
    @Override
    public double calculate(String ticketId) {
        return 0;
    }
}
