package org.iota.qubic.tangle;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tangle {
    Tangle origin;
    List<Transaction> basis;
    List<Tangle> subtangles;
    List<Transaction> tips;
    Map<String, Transaction> transactionMap;

    Set<Transaction> potentialTips;

    Set<String> invalidTransactions;

    /**
     * A semantically valid Qubic transaction
     * is attempted to attach to the current tangle context.
     *
     * It must attach to a valid subtangle
     * @param tx
     */
    public void addTransaction(Transaction tx) {
        for(String edge : tx.edges()) {
            if (invalidTransactions.contains(edge)) {
                return;
            }
            if (transactionMap.containsKey(edge)) {
                continue;
            }

        }
    }

}
