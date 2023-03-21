package com.github.hpopov.daqhats;

public final class ThermoCoupleValue {

    private static final double OPEN_TC_VALUE = -9999.0;
    /**
     * Value for thermocouple voltage outside the valid range.
     */
    private static final double OVERRANGE_TC_VALUE = -8888.0;
    /**
     * Value for thermocouple voltage outside the common-mode range.
     */
    private static final double COMMON_MODE_TC_VALUE = -7777.0;

    private final double temperature;

    public ThermoCoupleValue(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getStringValue() {
        if (temperature == OPEN_TC_VALUE) {
            return "Open";
        } else if (temperature == OVERRANGE_TC_VALUE) {
            return "OverRange";
        } else if (temperature == COMMON_MODE_TC_VALUE) {
            return "Common Mode";
        } else {
            return String.format("%12.2f C", temperature);
        }
    }
}
