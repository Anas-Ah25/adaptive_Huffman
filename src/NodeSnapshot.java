public class NodeSnapshot {
    public String symbol;
    public int count;
    public String code;
    public int nodeNumber;

    public NodeSnapshot left;
    public NodeSnapshot right;

    public NodeSnapshot(String symbol, int count, String code, int nodeNumber) {
        this.symbol = symbol;
        this.count = count;
        this.code = code;
        this.nodeNumber = nodeNumber;
    }
}
