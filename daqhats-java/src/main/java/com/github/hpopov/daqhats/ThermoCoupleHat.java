package com.github.hpopov.daqhats;

import java.util.Arrays;

public class ThermoCoupleHat implements AutoCloseable {

    public static ThermoCoupleHat open(TCHatId id, ThermoCoupleType defaultThermoCoupleType) {
        int channelAmount = openConnectionWithMcc134Hat(id.getAddress());
        if (channelAmount < 0) {
            throw new RuntimeException("Unable to open a connection with Hat by address " + id.getAddress());
        }
        for (int i = 0; i < channelAmount; i++) {
            setMcc134ThermoCoupleType(id.getAddress(), i, defaultThermoCoupleType.typeCode);
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
    private static native boolean setMcc134ThermoCoupleType(int address, int channel, int tcTypeCode);

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
            System.out.println("Channel " + channel + " is already initialized with " + thermoCoupleType);
        } else {
            boolean successful = setMcc134ThermoCoupleType(id.getAddress(), channel, thermoCoupleType.typeCode);
            if (!successful) {
                throw new RuntimeException("Unable to initialize " + channel + " with " + thermoCoupleType);
            }
        }
    }

    public ThermoCoupleValue readThermoCoupleValue(int channel) {
        verifyChannelIsValid(channel);
        ThermoCoupleValue value = readMcc134ThermoCoupleValue(id.getAddress(), channel);
        if (value == null) {
            throw new RuntimeException(
                    "Unable to read value from address" + id.getAddress() + " and channel " + channel);
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
            throw new RuntimeException("Unable to close a connection with Hat on address " + id.getAddress());
        }
    }

    private void verifyChannelIsValid(int channel) {
        if (channel >= getChannelAmount()) {
            throw new RuntimeException("Invalid channel supplied: " + channel + " is out of bounds "
                    + "[" + getLowestChannel() + ";" + getHighestChannel() + "]");
        }
    }

    private int getChannelAmount() {
        return thermoCoupleTypeByChannel.length;
    }
}
