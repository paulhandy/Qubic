package org.iota.model;

import org.iota.model.Transaction;

import java.util.List;

public class Fragment {
    public List<Transaction> transactions;
    public Transaction head;

    public void append(Transaction t) {
        transactions.add(t);
        head = t;
    }
}
