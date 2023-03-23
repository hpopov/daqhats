package com.github.hpopov.daqhats;

import java.util.stream.IntStream;

public class TCHatId {

    static {
        System.loadLibrary("daqhats-java");
    }

    public static TCHatId[] findConnectedHats() {
        return IntStream.of(findConnectedMcc134HatsAddresses())
                .mapToObj(TCHatId::new)
                .toArray(TCHatId[]::new);
    }

    private static native int[] findConnectedMcc134HatsAddresses();

    private final int address;

    private TCHatId(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }
}
