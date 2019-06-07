package org.iota.qubic.util;

public class Curl {
    public static final int HASH_LENGTH = 243;
    private static final int STATE_LENGTH = 3 * HASH_LENGTH;
    private static final byte[] TRUTH_TABLE = new byte[]{1,  0,  -1, 1,  0,  -1, 1,  0,  -1, 1,  0,
            -1, 1,  0,  -1, 1,  0,  -1, 1,  0,  -1, 1,
            0,  -1, 1,  0,  -1, 1,  0,  -1, 1,  0,  -1,
            1,  0,  -1, 1,  0,  -1, 1,  0,  -1, 1};
    private static final int NUMBER_OF_ROUNDS = 3;
    private static final byte[] INITIAL_STATE = new byte[STATE_LENGTH];

    private static final int[][] INDICES1 = new int[6][STATE_LENGTH];
    private static final int[][] INDICES2 = new int[6][STATE_LENGTH];

    private final byte[] state = new byte[STATE_LENGTH];
    private final byte[] scratchpad = new byte[STATE_LENGTH];

    static {

        int period = HASH_LENGTH;
        for (int i = 6; i-- > 0; ) {

            for (int j = 0; j < STATE_LENGTH; j++) {

                INDICES1[i][j] = j + (((j / period) + 1) % 3 - ((j / period) % 3)) * period;
                INDICES2[i][j] = j + (((j / period) + 2) % 3 - ((j / period) % 3)) * period;
            }

            period /= 3;
        }
    }

    void reset() {

        System.arraycopy(INITIAL_STATE, 0, state, 0, STATE_LENGTH);
    }

    public void absorb(final byte[] trits, int offset, int length) {

        do {

            System.arraycopy(trits, offset, state, 0, length < HASH_LENGTH ? length : HASH_LENGTH);
            transform();
            offset += HASH_LENGTH;

        } while ((length -= HASH_LENGTH) > 0);
    }

    public void squeeze(final byte[] trits, int offset, int length) {

        do {

            System.arraycopy(state, 0, trits, offset, length < HASH_LENGTH ? length : HASH_LENGTH);
            transform();
            offset += HASH_LENGTH;

        } while ((length -= HASH_LENGTH) > 0);
    }

    public void getInnerState(final byte[] trits, final int offset) {

        System.arraycopy(state, HASH_LENGTH, trits, offset, (STATE_LENGTH - HASH_LENGTH));
    }

    public void setInnerState(final byte[] trits, final int offset) {

        System.arraycopy(trits, offset, state, HASH_LENGTH, (STATE_LENGTH - HASH_LENGTH));
    }

    private void transform() {

        for (int r = NUMBER_OF_ROUNDS; r-- > 0; ) {

            for (int j = 0; j < STATE_LENGTH; j++) {

                scratchpad[j] = TRUTH_TABLE[state[j] + (state[INDICES1[5][j]] << 2) + (state[INDICES2[5][j]] << 4) + 21];
            }
            for (int j = 0; j < STATE_LENGTH; j++) {

                state[j] = TRUTH_TABLE[scratchpad[j] + (scratchpad[INDICES1[4][j]] << 2) + (scratchpad[INDICES2[4][j]] << 4) + 21];
            }
            for (int j = 0; j < STATE_LENGTH; j++) {

                scratchpad[j] = TRUTH_TABLE[state[j] + (state[INDICES1[3][j]] << 2) + (state[INDICES2[3][j]] << 4) + 21];
            }
            for (int j = 0; j < STATE_LENGTH; j++) {

                state[j] = TRUTH_TABLE[scratchpad[j] + (scratchpad[INDICES1[2][j]] << 2) + (scratchpad[INDICES2[2][j]] << 4) + 21];
            }
            for (int j = 0; j < STATE_LENGTH; j++) {

                scratchpad[j] = TRUTH_TABLE[state[j] + (state[INDICES1[1][j]] << 2) + (state[INDICES2[1][j]] << 4) + 21];
            }
            for (int j = 0; j < STATE_LENGTH; j++) {

                state[j] = TRUTH_TABLE[scratchpad[j] + (scratchpad[INDICES1[0][j]] << 2) + (scratchpad[INDICES2[0][j]] << 4) + 21];
            }
        }
    }
}
