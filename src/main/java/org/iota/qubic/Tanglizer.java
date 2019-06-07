package org.iota.qubic;

import org.iota.model.Fragment;
import org.iota.model.Transaction;
import org.iota.qcm.Qxi;

import java.util.HashMap;
import java.util.Map;

/**
 * Attached to:
 * Environment 0 - always tanglize things!
 */
public class Tanglizer implements Qxi {
    Map<String, Tanglizable> toDo = new HashMap<>();

    public Fragment tanglize(Tanglizable thing) {
        Fragment f = new Fragment();
        Transaction tx;
        String type = thing.type().reference();
        f.append(new Transaction());
        f.head.
        return f;
    }

    /**
     * Input arrays:
     *  - Transaction requested
     * @param input
     */

    @Override
    public void accept(byte[][] input) {

    }

    @Override
    public byte[][] read() {
        return new byte[0][];
    }
}
