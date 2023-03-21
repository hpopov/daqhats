package com.github.hpopov.daqhats;

import java.util.Arrays;

public class ThermoCoupleHat {

    public static ThermoCoupleHat open(TCHatId id, ThermoCoupleType defaultThermoCoupleType) {
        int channelAmount = openConnectionWithHat(id.getAddress());
        if (channelAmount < 0) {
            // Throw some exception: unable to open a connection with Hat by
            // address
        }
        for (int i = 0; i < channelAmount; i++) {
            setMcc134ThermoCoupleType(i, defaultThermoCoupleType.typeCode);
        }
        return new ThermoCoupleHat(id, channelAmount, defaultThermoCoupleType);
    }

    /**
     * Opens connection with Hat by specified address.
     * 
     * @param address
     * @return amount of channels available for this Hat or <b>-1</b> if
     *         operation was not successful
     */
    private static native int openConnectionWithHat(int address);

    /**
     * @return <b>false</b> if operation was not successful
     */
    private static native boolean setMcc134ThermoCoupleType(int channel, int tcTypeCode);

    /**
     * Reads ThermoCouple value by <code>address</code> and <code>channel</code>
     * 
     * @param address
     * @param channel
     * @return <b>null</b> if operation was not successful
     */
    private static native ThermoCoupleValue readMcc134ThermoCoupleValue(int address, int channel);

    private final TCHatId id;
    private final ThermoCoupleType[] thermoCoupleTypeByChannel;

    private ThermoCoupleHat(TCHatId id, int channelAmount, ThermoCoupleType defaultThermoCoupleType) {
        this.id = id;

        this.thermoCoupleTypeByChannel = new ThermoCoupleType[channelAmount];
        Arrays.fill(thermoCoupleTypeByChannel, defaultThermoCoupleType);
    }

    public void setThermoCoupleType(int channel, ThermoCoupleType thermoCoupleType) {
        verifyChannelIsValid(channel);
        if (thermoCoupleTypeByChannel[channel].equals(thermoCoupleType)) {
            // Log some message? channel is already initialized with
            // {thermoCoupleType}
        } else {
            boolean successful = setMcc134ThermoCoupleType(channel, thermoCoupleType.typeCode);
            if (!successful) {
                // Throw some exception: unable to initialize {channel} with
                // {thermoCoupleType}
            }
        }
    }

    public ThermoCoupleValue readThermoCoupleValue(int channel) {
        verifyChannelIsValid(channel);
        ThermoCoupleValue value = readMcc134ThermoCoupleValue(id.getAddress(), channel);
        if (value == null) {
            // Throw some exception: unable to read value from {id} and
            // {channel}
        }
        return value;
    }

    private void verifyChannelIsValid(int channel) {
        if (channel >= getChannelAmount()) {
            // Throw some exception: invalid channel
        }
    }

    private int getChannelAmount() {
        return thermoCoupleTypeByChannel.length;
    }
}
