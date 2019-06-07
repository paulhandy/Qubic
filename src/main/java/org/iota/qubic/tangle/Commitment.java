package org.iota.qubic.tangle;

public class Commitment extends Transaction {

    /**
     * If it is a commitment transaction, and the new one is also a commitment transaction,
     * then check whether it is legit
     * @param tx
     * @return
     */

    @Override
    public ReferenceResult reference(Transaction tx) {
        switch(tx.type()) {
            case COMMITMENT_TX:
                if (((Commitment)tx))
            default:
        }
        edges.put(tx.id(), tx);
        return ReferenceResult.SUCCESS;
    }

    @Override
    protected Type type() {
        return Type.COMMITMENT_TX;
    }

}
