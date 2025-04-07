public class AnimationEvent {

    public enum Type {
        SPLIT,
        SWAP,
        COUNTER
    }

    public Type type;
    public Node node1; // e.g. for SPLIT or COUNTER
    public Node node2; // used only for SWAP
    public long delay; // how long to wait before showing this event

    // Constructor for single-node events (SPLIT or COUNTER).
    public AnimationEvent(Type type, Node node, long delay) {
        this.type = type;
        this.node1 = node;
        this.delay = delay;
    }

    // Constructor for two-node events (SWAP).
    public AnimationEvent(Type type, Node node1, Node node2, long delay) {
        this.type = type;
        this.node1 = node1;
        this.node2 = node2;
        this.delay = delay;
    }
}
