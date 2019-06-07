package org.iota.qubic;

import java.lang.ref.WeakReference;
import java.util.Arrays;

public class Helix {
    static byte mask = 0b111111;
    byte[] lookup = new byte[64];
    byte in;
    byte out;
    Pin inputs[] = new Pin[3];
    Pin outputs[] = new Pin[3];

    public void update() {
        byte inm = (byte) ~in;
        int x = 3 & ~(inm | (inm >> 2) ^ (inm >> 4));
        //int x = (3 & ~(~(in | (in >> 2)) | (in >> 4)));
        out = (byte) ( mask & ((in << (x << 1)) | (in >>  ((3 & (~x)) << 1))));
    }
    public static class Pin {
        WeakReference<Helix> helix;
        byte value;

        public int get() {
            return value;
        }

        public void set(int i) {

        }
    }
}
