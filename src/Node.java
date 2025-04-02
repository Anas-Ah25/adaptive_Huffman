public class Node {

    private String symbol;        // The symbol (NYT / char)
    private String nodeBinCode;   // The binary code of the node
    private int nodeNumber;       // The node number (level in the tree)
    private int symbolCount;      // node counter
    private Node left;
    private Node right;
    private Node parent;

    public Node(String symbol, int nodeNumber) {
        this.symbol = symbol;
        this.nodeNumber = nodeNumber;
        this.symbolCount = 0;
        this.nodeBinCode = "";
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public boolean isRoot() {
        return parent == null;
    }
    public boolean isLeaf() {
        return left == null && right == null;
    }
    public void updateBinaryCode() {
        if (isRoot()) {
            nodeBinCode = "";
            return;
        }

        StringBuilder code = new StringBuilder();
        Node current = this;
        while (current.parent != null) {
            if (current == current.parent.left) {
                code.append("0");
            } else if (current == current.parent.right) {
                code.append("1");
            }
            current = current.parent;
        }
        nodeBinCode = code.reverse().toString();
    }

    // ************************ setters and getters ************************

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    //
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