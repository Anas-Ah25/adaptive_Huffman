public class SnapshotEvent {

    public enum Type {
        SPLIT,
        SWAP,
        COUNTER
    }

    public Type type;
    public TreeSnapshot snapshot;
    public String node1Symbol;
    public String node2Symbol;

    // single-node event (split or counter)
    public SnapshotEvent(Type type, TreeSnapshot snapshot, String node1Symbol) {
        this.type = type;
        this.snapshot = snapshot;
        this.node1Symbol = node1Symbol;
        this.node2Symbol = null;
    }

    // two-node event (swap)
    public SnapshotEvent(Type type, TreeSnapshot snapshot, String node1Symbol, String node2Symbol) {
        this.type = type;
        this.snapshot = snapshot;
        this.node1Symbol = node1Symbol;
        this.node2Symbol = node2Symbol;
    }
}
