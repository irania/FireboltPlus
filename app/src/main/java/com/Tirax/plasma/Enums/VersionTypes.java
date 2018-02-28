package com.Tirax.plasma.Enums;

/**
 * Created by a.irani on 11/4/2016.
 */
public enum VersionTypes {
    RF(0), CRYORF(1), FRACTIONAL(2), CAVITATION(3);
    private int value;
    private VersionTypes(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
