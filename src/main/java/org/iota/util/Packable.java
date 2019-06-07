package org.iota.util;

public interface Packable {
    byte[] pack();
    void unpack(byte[] bytes);
}
