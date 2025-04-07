public class Node {
    private String symbol;
    private String nodeBinCode;
    private int nodeNumber;
    private int symbolCount;
    private Node left;
    private Node right;
    private Node parent;

    public Node(String symbol, int nodeNumber) {
        this.symbol = symbol;
        this.nodeNumber = nodeNumber;
        this.symbolCount = 0;
        this.nodeBinCode = "";
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public void updateBinaryCode() {
        // Standard: left = 0, right = 1
        if (isRoot()) {
            nodeBinCode = "";
            return;
        }
        StringBuilder code = new StringBuilder();
        Node cur = this;
        while (cur.parent != null) {
            if (cur.parent.left == cur) {
                code.append("0");
            } else {
                code.append("1");
            }
            cur = cur.parent;
        }
        nodeBinCode = code.reverse().toString();
    }

    // ========== Getters/Setters ==========

    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNodeBinCode() {
        return nodeBinCode;
    }
    public void setNodeBinCode(String nodeBinCode) {
        this.nodeBinCode = nodeBinCode;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }
    public void setNodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    public int getSymbolCount() {
        return symbolCount;
    }
    public void setSymbolCount(int symbolCount) {
        this.symbolCount = symbolCount;
    }

    public Node getLeft() {
        return left;
    }
    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }
    public void setRight(Node right) {
        this.right = right;
    }

    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
}
