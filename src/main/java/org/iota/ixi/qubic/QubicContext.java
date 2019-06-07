package org.iota.ixi.qubic;

import org.iota.ict.ixi.context.IxiContext;
import org.json.JSONObject;

public class QubicContext implements IxiContext {
    @Override
    public String respondToRequest(String request) {
        /**
         * - Create a Committee
         * - View list of visible committees
         * - View list of committees in test phase
         * - Join committee [x]
         * - Start qubic [q]
         * - Get results for qubic [q] environment [v]
         * - Quit qubic [q]
         */
        return null;
    }

    @Override
    public void tryToUpdateConfiguration(JSONObject newConfiguration) {

    }

    @Override
    public JSONObject getConfiguration() {
        return null;
    }

    @Override
    public JSONObject getDefaultConfiguration() {
        return null;
    }
}
