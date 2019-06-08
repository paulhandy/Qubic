package org.iota.qubic.tangle;

import java.util.Map;
import java.util.Set;

public abstract class Transaction {
    public enum ReferenceResult {
        NON_SOLID,
        INVALID,
        SUCCESS,
    }

    public enum Type {
        COMMITMENT_TX
    }
    private String id;
    private Set<String> references;
    protected Map<String, Transaction> edges;

    public Transaction() {
    }

    public String id() {
        return id;
    }

    public String[] edges() {
        return references.toArray(new String[0]);
    }

    public abstract ReferenceResult reference(Transaction tx);
    protected abstract Type type();
}
