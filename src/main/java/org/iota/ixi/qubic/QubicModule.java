package org.iota.ixi.qubic;

import org.iota.ict.eee.call.EEEFunction;
import org.iota.ict.eee.call.FunctionEnvironment;
import org.iota.ict.eee.dispatch.ThreadedEffectDispatcherWithChainSupport;
import org.iota.ict.ixi.*;
import org.iota.ict.ixi.serialization.model.ClassFragment;
import org.iota.qubic.Qubic;
import org.iota.qubic.Supervisor;

public class QubicModule extends IxiModule {
    private Qubic qubic;

    private final EEEFunction createCommittee = new EEEFunction(new FunctionEnvironment("Qubic.ixi", "createCommittee"));

    public QubicModule(Ixi ixi) {
        super(ixi);
        qubic = new Qubic();
        ixi.addListener(createCommittee);
    }

    @Override
    public void run() {
        /**
         * load qubic....
         */
    }

    /**
     * Creates a committee transaction
     * @param args
     * @return
     */
    public String createCommittee(String args) {
    }
}
