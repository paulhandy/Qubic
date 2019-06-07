package org.iota.model;

import org.iota.util.Packable;

import java.nio.ByteBuffer;

public class Transaction implements Packable {
    public static int PACKED_LENGTH = Signature.PACKED_LENGTH + Hash.PACKED_LENGTH * 4 + Big.PACKED_LENGTH * 4 + Timestamp.PACKED_LENGTH * 6;
    Signature message;
    Hash extraDataDigest;
    Hash address;
    Big value;
    Timestamp issuanceTimestamp;
    Timestamp timeLockLowerBound;
    Timestamp timeLockUpperBound;
    Big bundleNonce;
    Hash trunkTransactionHash;
    Hash branchTransactionHash;
    Big tag;
    Timestamp attachmentTimestamp;
    Timestamp attachmentTimestampLowerBound;
    Timestamp attachmentTimestampUpperBound;
    Big nonce;

    @Override
    public byte[] pack() {
        ByteBuffer buffer = ByteBuffer.allocate(PACKED_LENGTH);
        buffer.put(message.pack());
        buffer.put(extraDataDigest.pack());
        buffer.put(address.pack());
        buffer.put(value.pack());
        buffer.put(issuanceTimestamp.pack());
        buffer.put(timeLockLowerBound.pack());
        buffer.put(timeLockUpperBound.pack());
        buffer.put(bundleNonce.pack());
        buffer.put(trunkTransactionHash.pack());
        buffer.put(branchTransactionHash.pack());
        buffer.put(tag.pack());
        buffer.put(attachmentTimestamp.pack());
        buffer.put(attachmentTimestampLowerBound.pack());
        buffer.put(attachmentTimestampUpperBound.pack());
        buffer.put(nonce.pack());
        return buffer.array();
    }

    @Override
    public void unpack(byte[] bytes) {
    }
}
