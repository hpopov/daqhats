package com.github.hpopov.daqhats;

public enum ThermoCoupleType {

    J(0),
    K(1);

    ThermoCoupleType(int typeCode) {
        this.typeCode = typeCode;
    }

    public final int typeCode;
}
