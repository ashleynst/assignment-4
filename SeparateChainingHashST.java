public class SeparateChainingHashST<Key, Value>
{
    private int M = 1000;
    private int comparisons = 0;
    private boolean useOldHash;
    private Node[] st = new Node[M];

    private static class Node {
        private Object key;
        private Object val;
        private Node next;

        public Node(Object key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public SeparateChainingHashST(boolean useOldHash)
    {
        this.useOldHash = useOldHash;
    }

    public int hash(Key key)
    {
        if (useOldHash) {
            return (HashFunctions.hashCodeOld(key.toString()) & 0x7fffffff) % M;
        } else {
            return (HashFunctions.hashCodeCurr(key.toString()) & 0x7fffffff) % M;
        }
    }

    public Value get(Key key){
       int i = hash(key);
       comparisons = 0;
       for(Node x = st[i]; x != null; x = x.next)
       {
           comparisons++;
           if(key.equals(x.key)) return (Value) x.val;
       }
       return null;
    }

    public void put(Key key, Value val){
        int i = hash(key);
        for(Node x = st[i]; x != null; x = x.next)
        {
            if(key.equals(x.key))
            {
                x.val = val;
                return;
            }
        }
        st[i] = new Node(key, val, st[i]);
    }


    public int getComparisons() {
        return comparisons;
    }

}