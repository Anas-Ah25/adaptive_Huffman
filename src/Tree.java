public class Tree {
    private Node root;
    private Node nytNode;
    private int NodePosition; // numbering from ~100 down

    public Tree() {
        NodePosition = 100;
        root = new Node("NYT", NodePosition--);
        nytNode = root;
    }

    public Node getRoot() {
        return root;
    }

    public Node getCurrentNTY() {
        return nytNode;
    }

    public Node getNode(String symbol) {
        return findNode(root, symbol);
    }

    private Node findNode(Node node, String symbol) {
        if (node == null) return null;
        if (symbol.equals(node.getSymbol())) return node;
        Node left = findNode(node.getLeft(), symbol);
        if (left != null) return left;
        return findNode(node.getRight(), symbol);
    }

    // =============== SPLIT ===============
    public void split(String symbol) {
        if (root == null) {
            root = new Node("NYT", NodePosition--);
            nytNode = root;
        }
        Node oldNyt = nytNode;
        Node newNyt = new Node("NYT", NodePosition--);
        newNyt.setSymbolCount(0);

        Node symNode = new Node(symbol, NodePosition--);
        symNode.setSymbolCount(1);

        oldNyt.setLeft(newNyt);
        oldNyt.setRight(symNode);
        newNyt.setParent(oldNyt);
        symNode.setParent(oldNyt);

        // oldNyt now internal
        oldNyt.setSymbol(null);
        oldNyt.setSymbolCount(1);

        nytNode = newNyt;
        newNyt.updateBinaryCode();
        symNode.updateBinaryCode();

        // Record a SPLIT snapshot
        recordEventSnapshot(
                SnapshotEvent.Type.SPLIT,
                oldNyt.getSymbol() == null ? "INT" : oldNyt.getSymbol(),
                null
        );
    }

    // =============== UPDATE (counter increments) ===============
    public void update(Node node, boolean isNewSymbol) {
        boolean first = true;
        while (node != null) {
            Node nodeToSwap = swapWithW(node);
            if (nodeToSwap != null) {
                swap(node, nodeToSwap);
            }
            if (!first || !isNewSymbol) {
                node.setSymbolCount(node.getSymbolCount() + 1);
                // record a COUNTER snapshot
                recordEventSnapshot(
                        SnapshotEvent.Type.COUNTER,
                        (node.getSymbol() == null || node.getSymbol().isEmpty()) ? "INT" : node.getSymbol(),
                        null
                );
            }
            if (!node.isLeaf()) {
                int sum = 0;
                if (node.getLeft() != null) sum += node.getLeft().getSymbolCount();
                if (node.getRight() != null) sum += node.getRight().getSymbolCount();
                node.setSymbolCount(sum);
            }
            node = node.getParent();
            first = false;
        }
    }
    public void update(Node node) {
        update(node, false);
    }

    // =============== SWAP ===============
    public void swap(Node n1, Node n2) {
        if (n1 == null || n2 == null || n1 == n2) return;
        if (n1.getParent() == n2 || n2.getParent() == n1) return;

        Node p1 = n1.getParent();
        Node p2 = n2.getParent();
        boolean n1Left = (p1 != null && p1.getLeft() == n1);
        boolean n2Left = (p2 != null && p2.getLeft() == n2);

        // re-wire
        if (p1 == null) {
            root = n2;
        } else {
            if (n1Left) p1.setLeft(n2); else p1.setRight(n2);
        }
        if (p2 == null) {
            root = n1;
        } else {
            if (n2Left) p2.setLeft(n1); else p2.setRight(n1);
        }
        n1.setParent(p2);
        n2.setParent(p1);

        // swap nodeNumber
        int tmp = n1.getNodeNumber();
        n1.setNodeNumber(n2.getNodeNumber());
        n2.setNodeNumber(tmp);

        updateNodeCodes(n1);
        updateNodeCodes(n2);

        // record SWAP snapshot with both symbols
        String s1 = (n1.getSymbol() == null || n1.getSymbol().isEmpty()) ? "INT" : n1.getSymbol();
        String s2 = (n2.getSymbol() == null || n2.getSymbol().isEmpty()) ? "INT" : n2.getSymbol();
        recordEventSnapshot(SnapshotEvent.Type.SWAP, s1, s2);
    }

    public Node swapWithW(Node node) {
        int w = node.getSymbolCount();
        return findSwapCandidate(root, w, node, node.getNodeNumber());
    }

    private Node findSwapCandidate(Node cur, int weight, Node exclude, int maxNumber) {
        if (cur == null) return null;
        Node best = null;
        if (cur != exclude && cur.getSymbolCount() == weight && !isAncestor(cur, exclude)) {
            if (cur.getNodeNumber() > maxNumber) {
                best = cur;
                maxNumber = cur.getNodeNumber();
            }
        }
        Node left = findSwapCandidate(cur.getLeft(), weight, exclude, maxNumber);
        if (left != null && (best == null || left.getNodeNumber() > best.getNodeNumber())) {
            best = left;
            maxNumber = best.getNodeNumber();
        }
        Node right = findSwapCandidate(cur.getRight(), weight, exclude, maxNumber);
        if (right != null && (best == null || right.getNodeNumber() > best.getNodeNumber())) {
            best = right;
        }
        return best;
    }

    private boolean isAncestor(Node anc, Node desc) {
        while (desc != null) {
            if (desc == anc) return true;
            desc = desc.getParent();
        }
        return false;
    }

    public void updateNodeCodes(Node node) {
        if (node == null) return;
        node.updateBinaryCode();
        updateNodeCodes(node.getLeft());
        updateNodeCodes(node.getRight());
    }

    // =============== HELPER: record snapshot after an event ===============
    private void recordEventSnapshot(SnapshotEvent.Type type, String node1Symbol, String node2Symbol) {
        TreeSnapshot snap = createSnapshot();
        SnapshotEvent evt;
        if (type == SnapshotEvent.Type.SWAP) {
            evt = new SnapshotEvent(type, snap, node1Symbol, node2Symbol);
        } else {
            evt = new SnapshotEvent(type, snap, node1Symbol);
        }
        SnapshotRecorder.record(evt);
    }

    // =============== CREATE SNAPSHOT ===============
    public TreeSnapshot createSnapshot() {
        NodeSnapshot rootSnap = copyNode(root);
        return new TreeSnapshot(rootSnap);
    }

    private NodeSnapshot copyNode(Node node) {
        if (node == null) return null;
        String sym = (node.getSymbol() == null) ? "" : node.getSymbol();
        String code = (node.getNodeBinCode() == null) ? "" : node.getNodeBinCode();
        NodeSnapshot ns = new NodeSnapshot(sym, node.getSymbolCount(), code, node.getNodeNumber());
        ns.left = copyNode(node.getLeft());
        ns.right = copyNode(node.getRight());
        return ns;
    }
}
