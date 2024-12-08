public class LinearProbingHashST<Key, Value> {
    private int M = 20000;
    private int comparisons = 0;
    private boolean useOldHash;
    private Key[] keys = (Key[]) new Object[M];;
    private Value[] values = (Value[]) new Object[M];;

    public LinearProbingHashST(boolean useOldHash) {
        this.useOldHash = useOldHash;
    }

    private int hash(Key key) {
        if (useOldHash) {
            return (HashFunctions.hashCodeOld(key.toString()) & 0x7fffffff) % M;
        } else {
            return (HashFunctions.hashCodeCurr(key.toString()) & 0x7fffffff) % M;
        }
    }

    public Value get(Key key) {
       comparisons = 0;
       int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            comparisons++;
            if (keys[i].equals(key)) {
                return values[i];
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                break;
            }
        }
        keys[i] = key;
        values[i] = val;
    }

    public int getComparisons() {
        return comparisons;
    }

}

