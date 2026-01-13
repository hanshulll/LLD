package org.hanshul.strategy.fee;

import org.hanshul.strategy.fee.FeeCalculationStrategy;

import java.time.Instant;

public class FlatFeeCalculation implements FeeCalculationStrategy {
    private final double parkingRate = 8.0;

    @Override
    public double calculate(String ticketId) {
        Instant now = Instant.now();
        return 0;
    }
}
