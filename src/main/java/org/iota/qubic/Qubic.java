package org.iota.qubic;

import org.iota.ict.utils.Trytes;
import org.iota.model.Hash;
import org.iota.qcm.Qxi;
import org.iota.qubic.tangle.TangleProvider;
import org.iota.qubic.util.Curl;
import org.omg.CORBA.UNKNOWN;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.function.Function;

public class Qubic extends Qxi {
    private TangleProvider tangle;
    Set<Committee> joinedCommittees = new HashSet<>();
    Set<Committee> committees = new HashSet<>();
    Map<String, QTx> qTxMap = new HashMap<>();

    public void join(Committee committee) {
      joinedCommittees.add(committee);
    }

    public void qubicTxArrived(byte[] qTx) {
        QTx tx = new QTx(qTx);
        /* determine if it's new */
        /* send it to committee if known */
        tx = qTxMap.merge(tx.id, tx, (qTx1, qTx2) -> {
            if (qTx1 == null) {
                return qTx2;
            }
            return qTx1;
        });
    }

    public static class QTx {
        enum Type {
            Q_TX_META,
            Q_TX_TEST,
            Q_TX_COMMIT,
            Q_TX_VOTE,
            Q_TX_QUORUM,
        }

        public String id;
        public Type type;
        public String[] edges;

        public QTx(byte[] transactionData) {
            id = new String(transactionData);
            /*
            Determine QTx
             */
        }


    }

    public static class QTangle {
        private final Set<Hash> tipsSet = new HashSet<>();
        private final Map<Hash, Set<Hash>> commitSet = new HashMap<>();

        public void addTransaction(QTx tx) {
            switch (tx.type) {
                case Q_TX_META:
                case Q_TX_TEST:
                case Q_TX_COMMIT:
                case Q_TX_VOTE:
                case Q_TX_QUORUM:
            }
        }
    }

    public static class Committee {
        enum Phase {
            TEST_PHASE,
            PROC_PHASE
        }
        Epoch latestEpoch;
        QTx rulesId;
        String id;
        long duration, testDuration;
        long threshold;
        Map<String, Long> oracleWeights;
        Map<String, Function<QTx, Long>> testMap;
        Map<String, Set<Commitment>> commitmentsMap;
        Map<String, Result> resultMap;
        Map<String, Qcm.Environment> commonEnvironments;

        private Set<Result> toReveal;

        /* How to identify a committee?
                1: by the rules tx, hashed to each Epoch
         */

        /** create a committee */
        public void create() {}
        /** * Join the committee next chance you get! (do test) */
        public void join() { }
        /** Do not renew position in next epoch. */
        public void resign() { }

        /** resource test phase */

        public void test(int testIndex) {}
        public void testval(String hash) {}

        public void addCommitment(Commitment commitment) {
            /*
            if (!oracleWeights.containsKey(commitment.oracle.id)) {
                return AddTransactionStatus.INVALID_ID;
            }
             */

            Result result = resultMap.getOrDefault(commitment.hash, new Result());

            result.commit(commitment);
            result.update();
            if (result.enumerate() > threshold && !result.revealed) {
                toReveal.add(result);
            }
        }

        /** processing phase */

        /**
         * Commit a result to the quorum
         * @param resultEnvironment where the result should go when a quorum is reached
         * @param result the data I vote on.
         */
        public void commit(String resultEnvironment, byte[] result) {
            Commitment commitment = new Commitment();
            commitment.environment = commonEnvironments.merge(resultEnvironment, new Qcm.Environment(), (environment, environment2) -> {
                if (environment != null) {
                    return environment;
                }
                environment2.id = resultEnvironment;
                return environment2;
            });
            /* publish commitment */
            throw new NotImplementedException();
        }

        public void reveal(String commitId) {
            Commitment commitment = commitmentsMap.get(commitId);
            if(commitment != null) {
                /* get tips */

                /* publish result */
                throw new NotImplementedException();
            }
        }

        /** input effect of value with quorum on it to its dest environment */
        public void quorum () {
            /* test whether the reveal is already input */

        }

        /** vote on a change to the quorum rules*/
        public void vote(String changeHash) {}


        /**
         * Add a transaction to the committee
         * @param tx
         */
        public void addTx(QTx tx) {
            /* Check edges are valid or known */
            /* Validate q-tx data */
            {
                /*
                Types of Q-Tx include:
                    - Resource test
                        * data is identified and associated with committee rules
                    - Commitement
                        * has some commitment ID, [and metadata type of commitment?]
                    - Reveal
                        * refers to commitments
                    - Computation request
                        * contains the branches and metadata for computation
                    - Committee rule diff request
                    - Committee rule diff vote
                    - [sub-]Committee rule-change quorum
                 */
            }
            /* Validate q-tx in context of subtangle */
            throw new NotImplementedException();
        }

        public static class Epoch {
            Set<QTx> dawn, tips;

        }
    }

    public static class Oracle {
        String id;
        long weight;
    }

    public static class Commitment {
        Oracle oracle;
        String saltedHash, hash;
    }

    public static class Result {
        Qcm.Environment environment;
        Qcm.Effect effect;
        byte[] value;
        byte[] capacity;
        String hash; // trytes hash
        boolean revealed;
        Set<Commitment> unvalidatedCommitments;
        Set<Commitment> validCommitments;

        public void hash(byte[] env, byte[] value) {
            byte[] hash = new byte[Curl.HASH_LENGTH];
            capacity = new byte[Curl.CAPACITY_LENGTH];
            Curl curl = new Curl();
            curl.absorb(env, 0, env.length);
            curl.absorb(value, 0, value.length);
            curl.squeeze(hash, 0, hash.length);
            curl.getInnerState(capacity, 0);
        }

        public byte[] salted(byte[] salt){
            byte[] saltedHash = new byte[Curl.HASH_LENGTH];
            Curl curl = new Curl();
            curl.setInnerState(capacity, 0);
            curl.absorb(salt, 0, salt.length);
            curl.squeeze(saltedHash, 0, Curl.HASH_LENGTH);
            return saltedHash;
        }

        enum SaltValidation {
            INVALID,
            UNKNOWN,
            VALID,
        }

        public SaltValidation validate(Commitment commitment) {
            if (capacity == null) {
                return SaltValidation.UNKNOWN;
            }
            return commitment.saltedHash.equals(salted(Trytes.toTrits(commitment.oracle.id))) ?
                SaltValidation.VALID : SaltValidation.INVALID;
        }

        public void commit(Commitment commitment) {
          switch (validate(commitment)) {
              case UNKNOWN:
                  unvalidatedCommitments.add(commitment);
                  break;
              case VALID:
                  validCommitments.add(commitment);
                  break;
              case INVALID:
                  break;
          }
        }

        void update() {
            for (Commitment commitment: unvalidatedCommitments) {
                switch (validate(commitment)) {
                    case VALID:
                        validCommitments.add(commitment);
                        break;
                    case INVALID:
                        unvalidatedCommitments.remove(commitment);
                        break;
                    default: break;
                }
            }
        }

        public long enumerate() {
            return validCommitments.stream().map(commitment -> commitment.oracle.weight ).reduce(Math::addExact).get();
        }

        /**
         * setValue
         * sets the value of the result (which may have commitments before values)
         * @param env the environment to which the value is destined
         * @param val the value of the result
         * @return an enumeration of all valid votes
         */
        public long setValue(byte[] env, byte[] val) {
            environment.id = env;
            value = val;
            hash(env, value);
            update();
            return enumerate();
        }
    }

    public static class Apocalypse {
        Qcm.Effect effect;
    }


    public static abstract class QNode {

    }

    /** A COMMITTEE Announcement
     *      - Tail Extra Data Digest : Type Qubic Committee Announcement/Rules
     *      - Address : Tx ID of Rules Tail
     *      - Edge count (0)
     */
}

