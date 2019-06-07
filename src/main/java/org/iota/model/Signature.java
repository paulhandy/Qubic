package org.iota.model;

import org.iota.util.Packable;

public class Signature implements Packable {
    public static final int TRIT_LENGTH = Hash.TRIT_LENGTH * 27;
    public static final int PACKED_LENGTH = TRIT_LENGTH * 2 / 9;

    @Override
    public byte[] pack() {
        return new byte[0];
    }
}
