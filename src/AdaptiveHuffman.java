public class AdaptiveHuffman {
    private String input;
    private String encodedString;
    private String decodedString;
    private Tree tree;

    public AdaptiveHuffman(String input) {
        this.tree = new Tree();
    }

    public void updateTree(String symbol) {
        Node node = tree.getNode(symbol);
        if (node == null) { // if the symbol is not in the tree
            oldNYT  = tree.getCurrentNTY(); // get the current NYT node before we split, as we will move from it after the split
            oldNYT.setSymbolCount(oldNYT.getSymbolCount() + 1); // update the symbol count of the old NYT node
            // increment the counter of the nyt
            tree.split(symbol);
            tree.updateSymbolCount(symbol);
            // now get back to the old NYT node, and see if it is a root, if so, return, but if not, go to parent and search for swap conditions
            if (oldNYT.isRoot()) {
                return;
            } else {
                Node parent = oldNYT.getParent();
                while (parent != null) {

                }
            }
        } else { // if the symbol is in the tree
            // go to the node, see swap conditions, swap if needed, then in both cases increment the counter of the counter of the symbol node
            // see if now this is a root, if so, return, but if not, go to parent and search for swap conditions
        }
        tree.updateNodeCodes(tree.getRoot());
    }


}
