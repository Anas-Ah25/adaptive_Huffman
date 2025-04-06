public class Tree {
    private Node root;
    private Node nytNode;
    private int NodePosition; // number of the root node

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

    private Node nodeSearch(Node node, String symbol) { // search for a node with the given symbol
        if (node == null) {
            return null;
        }
        if (node.getSymbol() != null && node.getSymbol().equals(symbol)) {
            return node;
        }
        Node leftResult = nodeSearch(node.getLeft(), symbol);
        if (leftResult != null) {
            return leftResult;
        }
        return nodeSearch(node.getRight(), symbol);
    }

    public Node getNode(String symbol) {
        return nodeSearch(root, symbol);
    }

    public void updateNodeCodes(Node node) {
        if (node == null) {
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
        Node newNyt = new Node("NYT", NodePosition--);
        newNyt.setSymbolCount(0); // NYT starts with count 0
        Node symbolNode = new Node(symbol, NodePosition--);
        symbolNode.setSymbolCount(1); // new symbol starts with count 1

        oldNyt.setLeft(newNyt);
        oldNyt.setRight(symbolNode);
        newNyt.setParent(oldNyt);
        symbolNode.setParent(oldNyt);
        oldNyt.setSymbol(null); // old NYT becomes an internal node
        oldNyt.setSymbolCount(1); // old NYT (now internal) gets count 1
        nytNode = newNyt;

        newNyt.updateBinaryCode();
        symbolNode.updateBinaryCode();
    }

    public void update(Node node, boolean isNewSymbol) { // algorithm after initial conditions
        boolean isFirstNode = true; // skip increment for new symbol node
        while (node != null) {
            // swap and increment
            Node nodeToSwap = swapWithW(node);
            if (nodeToSwap != null) {
                swap(node, nodeToSwap);
            }
            if (!isFirstNode || !isNewSymbol) { // Increment unless it's the first node AND a new symbol
                node.setSymbolCount(node.getSymbolCount() + 1); // increment the counter
            }
            if (!node.isLeaf()) {
                int childrenSum = 0;
                if (node.getLeft() != null) {
                    childrenSum += node.getLeft().getSymbolCount();
                }
                if (node.getRight() != null) {
                    childrenSum += node.getRight().getSymbolCount();
                }
                node.setSymbolCount(childrenSum); // update internal node count to the sum of its children
            }
            node = node.getParent();
            isFirstNode = false;
        }
    }

    // Add this to maintain compatibility with Decoder
    public void update(Node node) {
        update(node, false); // Default to not a new symbol
    }

    public void swap(Node node1, Node node2) {
        if (node1==null || node2==null || node1==node2) {
            return;
        }
        if (node1.getParent() == node2 || node2.getParent() == node1){
            return;
        }

        Node parent1 = node1.getParent();
        Node parent2 = node2.getParent();
        boolean isNode1Left = parent1 != null && parent1.getLeft() == node1;
        boolean isNode2Left = parent2 != null && parent2.getLeft() == node2;

        if (parent1 != null) {
            if (isNode1Left){
                parent1.setLeft(node2);
            }
            else{
                parent1.setRight(node2);
            }
        } else {
            root = node2;
        }

        if (parent2 != null) {
            if (isNode2Left){
                parent2.setLeft(node1);
            }
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
        int weight = node.getSymbolCount();
        Node secNode = BestNode(root,weight,node, node.getNodeNumber());
        return secNode;
    }

    private Node BestNode(Node current, int targetWeight, Node excludeNode, int maxNodeNumber) {
        if (current == null) {
            return null;
        }

        Node secNode = null;
        // Check the current node
        if (current.getSymbolCount() == targetWeight && current != excludeNode && !isUpper(current, excludeNode)) {
            if (current.getNodeNumber() > maxNodeNumber) {
                maxNodeNumber = current.getNodeNumber();
                secNode = current;

            }
        }

        // Recursively search left and right subtrees
        Node leftBestN = BestNode(current.getLeft(), targetWeight, excludeNode, maxNodeNumber);
        if (leftBestN != null && (secNode == null || leftBestN.getNodeNumber() > secNode.getNodeNumber())) {
            secNode = leftBestN;
            maxNodeNumber = secNode.getNodeNumber();
        }

        Node rightBestN = BestNode(current.getRight(), targetWeight, excludeNode, maxNodeNumber);
        if (rightBestN != null && (secNode == null || rightBestN.getNodeNumber() > secNode.getNodeNumber())) {
            secNode = rightBestN;
        }

        return secNode;
    }

    private boolean isUpper(Node ancestor, Node descendant) {
        Node current = descendant;
        while (current != null) {
            if (current == ancestor) return true;
            current = current.getParent();
        }
        return false;
    }
}