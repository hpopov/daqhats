package com.github.hpopov.daqhats;

import java.util.Arrays;

public class ThermoCoupleHat implements AutoCloseable {

    public static ThermoCoupleHat open(TCHatId id, ThermoCoupleType defaultThermoCoupleType) {
        int channelAmount = openConnectionWithMcc134Hat(id.getAddress());
        if (channelAmount < 0) {
            throw new RuntimeException("unable to open a connection with Hat by address " + id.getAddress());
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
    private static native int openConnectionWithMcc134Hat(int address);

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

    /**
     * Closes the connection with Hat by specified address.
     * 
     * @param address
     * @return <b>false</b> if operation was not successful
     */
    private static native boolean closeConnectionWithMcc134Hat(int address);

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

    public int getLowestChannel() {
        return 0;
    }

    public int getHighestChannel() {
        return getChannelAmount() - 1;
    }

    @Override
    public void close() {
        if (!closeConnectionWithMcc134Hat(id.getAddress())) {
            // Throw some exception: unable to open a connection with Hat by
            // address
        }
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
