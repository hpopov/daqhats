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

    private static String getStringValue(double temperature) {
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

    private final double temperature;
    private final String value;

    /**
     * <p>
     * Constructor, demonstrating how <code>String</code> <i>value</i> is
     * constructed in C++.
     * </p>
     * <p>
     * For demo purposes only.
     * </p>
     * 
     * @param temperature
     */
    public ThermoCoupleValue(double temperature) {
        this(temperature, getStringValue(temperature));
    }

    public ThermoCoupleValue(double temperature, String value) {
        this.temperature = temperature;
        this.value = value;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean isValid() {
        return value == null;
    }

    public String getValue() {
        if (value == null) {
            return String.format("%12.2f C", temperature);
        }
        return value;
    }
}
