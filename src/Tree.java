public class Tree {
    private Node root;
    private Node nytNode;
    private int NodePosition;

    public Tree() {
        this.root = null;
        this.nytNode = null;
        this.NodePosition = 100; // number of the root node
    }


    /* how do we insert in the tree?, we are on some node,then split by to nodes, one on left and one on right ,
      the left one will always be nyt node, and right is the node with symbol we want */

    public void split(String symbol, Node currentNode) {
        Node oldNyt = currentNode;
        Node newNyt = new Node("NYT", NodePosition--);
        Node symbolNode = new Node(symbol, NodePosition--);

        // Set up the tree structure
        oldNyt.setLeft(newNyt);
        oldNyt.setRight(symbolNode);
        newNyt.setParent(oldNyt);
        symbolNode.setParent(oldNyt);
        newNyt.updateBinaryCode();
        symbolNode.updateBinaryCode();
    }
    public void swap(Node node1, Node node2) {
        return;
    }
    public Node swapWithW(Node node) { // search for the suitable node to swap
        return;
    }

}