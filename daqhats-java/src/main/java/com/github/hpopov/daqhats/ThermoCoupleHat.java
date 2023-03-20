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
            setThermoCoupleType(i, defaultThermoCoupleType.typeCode);
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
     * @return <code>false</code> if operation was not successful
     */
    private static native boolean setThermoCoupleType(int channel, int tcTypeCode);

    private final TCHatId id;
    private final ThermoCoupleType[] thermoCoupleTypeByChannel;

    private ThermoCoupleHat(TCHatId id, int channelAmount, ThermoCoupleType defaultThermoCoupleType) {
        this.id = id;

        this.thermoCoupleTypeByChannel = new ThermoCoupleType[channelAmount];
        Arrays.fill(thermoCoupleTypeByChannel, defaultThermoCoupleType);
    }

    private int getChannelAmount() {
        return thermoCoupleTypeByChannel.length;
    }
}
