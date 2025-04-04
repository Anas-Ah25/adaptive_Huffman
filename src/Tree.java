public class Tree {
    private Node root;
    private Node nytNode;
    private int NodePosition;

    public Tree() {
        this.NodePosition = 100;
        this.root = new Node("NYT", NodePosition--);
        this.nytNode = root;
    }

    public Node getRoot() {
        return root;
    }

    public Node getCurrentNTY() {
        return nytNode;
    }

    private Node nodeSearch(Node node,String symbol) { //
        if (node == null){
            return null;
        }
        if (node.getSymbol() != null && node.getSymbol().equals(symbol)){
            return node;
        }
        Node leftResult = nodeSearch(node.getLeft(),symbol);
        if (leftResult != null){
            return leftResult;
        }
        return nodeSearch(node.getRight(),symbol);
    }
    public Node getNode(String symbol) {
        return nodeSearch(root,symbol);
    }

    public void updateNodeCodes(Node node){
        if (node == null){
            return;
        }
        node.updateBinaryCode();
        updateNodeCodes(node.getLeft());
        updateNodeCodes(node.getRight());
    }

    public void split(String symbol) {
        if (root == null) {
            root = new Node("NYT", NodePosition--);
            nytNode = root;
        }


        Node oldNyt = nytNode;
        oldNyt.setSymbolCount(oldNyt.getSymbolCount() + 1); // (1) update the symbol count
        Node newNyt = new Node("NYT", NodePosition--);
        Node symbolNode = new Node(symbol, NodePosition--);

        oldNyt.setLeft(newNyt);
        oldNyt.setRight(symbolNode);
        newNyt.setParent(oldNyt);
        symbolNode.setParent(oldNyt);
        nytNode = newNyt;
        symbolNode.setSymbolCount(1); // (2) update the symbol count of the new node
        newNyt.updateBinaryCode();
        symbolNode.updateBinaryCode();

    }

    public void swap(Node node1, Node node2) {
        Node parent1 = node1.getParent();
        Node parent2 = node2.getParent();
        boolean isNode1Left = parent1 != null && parent1.getLeft() == node1;
        boolean isNode2Left = parent2 != null && parent2.getLeft() == node2;
        if (parent1 != null) {
            if (isNode1Left) parent1.setLeft(node2);
            else{
                parent1.setRight(node2);
            }
        } else {
            root = node2;
        }

        if (parent2 != null) {
            if (isNode2Left) parent2.setLeft(node1);
            else {
                parent2.setRight(node1);
            }
        } else {
            root = node1;
        }

        node1.setParent(parent2);
        node2.setParent(parent1);

        int temp = node1.getNodeNumber();
        node1.setNodeNumber(node2.getNodeNumber());
        node2.setNodeNumber(temp);

        updateNodeCodes(node1);
        updateNodeCodes(node2);
    }

    public Node swapWithW(Node node) { // search for the suitable node to swap
        return null;
    }

    public Boolean canSwap(Node node1, Node node2) { // check if the two nodes can be swapped
        return false;
    }

//    public void updateSymbolCount(String symbol) {
//        return;
//    }
}