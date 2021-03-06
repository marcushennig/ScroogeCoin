package crypto.transaction;

import crypto.shared.Base58;

import java.util.Arrays;

/**
 * Represents an unspent transaction output.
 * An Unspent Transaction Output (UTXO) that can be spent as an input in a new transaction.
 * Essentially it points the unspent output of the transaction
 * Check: https://bitcoin.org/en/glossary/unspent-transaction-output
 */
public class UTXO implements Comparable<UTXO> {

    /** Hash of the transaction from which this UTXO originates */
    private byte[] txHash;

    /** Index of the corresponding output in said transaction */
    private int index;

    /**
     * Creates a new UTXO corresponding to the output with index <index> in the transaction whose
     * hash is {@code txHash}
     * @param txHash Hash of the transaction from which this UTXO originates
     * @param index Index of the corresponding output in said transaction
     */
    public UTXO(byte[] txHash, int index) {

        this.txHash = Arrays.copyOf(txHash, txHash.length);
        this.index = index;
    }

    /** @return the transaction hash of this UTXO */
    public byte[] getTxHash() {
        return txHash;
    }

    /** @return the index of this UTXO */
    public int getIndex() {
        return index;
    }

    /**
     * Compares this UTXO to the one specified by {@code other}, considering them equal if they have
     * {@code txHash} arrays with equal contents and equal {@code index} values
     */
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        UTXO other = (UTXO) obj;

        if (other.txHash.length != this.txHash.length || other.index != this.index) {
            return false;
        }

        for (int i = 0; i < other.txHash.length; i++) {
            if (other.txHash[i] != txHash[i])
                return false;
        }
        return true;
    }

    /**
     * Simple implementation of a UTXO hashCode that respects equality of UTXOs // (i.e.
     * utxo1.equals(utxo2) => utxo1.hashCode() == utxo2.hashCode())
     */
    public int hashCode() {

        int hash = 1;

        hash = hash * 17 + index;
        hash = hash * 31 + Arrays.hashCode(txHash);

        return hash;
    }

    /** Compares this UTXO to the one specified by {@code other} */
    public int compareTo(UTXO other) {

        if (other.index > this.index)
            return -1;

        else if (other.index < this.index)
            return 1;

        else {

            int len1 = this.txHash.length;
            int len2 = other.txHash.length;

            if (len2 > len1)
                return -1;

            else if (len2 < len1)

                return 1;

            else {

                for (int i = 0; i < len1; i++) {

                    if (other.txHash[i] > this.txHash[i])
                        return -1;

                    else if (other.txHash[i] < this.txHash[i])
                        return 1;
                }
                return 0;
            }
        }
    }

    @Override
    public String toString() {

        return String.format("Tx:%s@%d", Base58.encode(this.txHash), this.index);
    }
}
