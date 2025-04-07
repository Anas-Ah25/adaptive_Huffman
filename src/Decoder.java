public class Decoder {
    private Tree tree;

    public Decoder(Tree tree) {
        this.tree = tree;
    }

    public String decode(String encoded) {
        StringBuilder decoded = new StringBuilder();
        int i = 0;
        while (i < encoded.length()) {
            Node current = tree.getRoot();
            while (!current.isLeaf()) {
                if (i >= encoded.length()) break;
                char bit = encoded.charAt(i++);
                if (bit == '0') current = current.getLeft();
                else current = current.getRight();
            }
            if (!current.isLeaf()) {
                System.err.println("did not reach a leaf??");
                break;
            }
            String symbol = current.getSymbol();
            if (symbol == null) {
                System.err.println("null symbol??");
                break;
            }
            if (symbol.equals("NYT")) {
                if (i + 8 > encoded.length()) {
                    System.err.println("not enough bits for new symbol");
                    break;
                }
                String shortCode = encoded.substring(i, i+8);
                i += 8;
                char c = (char) Integer.parseInt(shortCode, 2);
                symbol = String.valueOf(c);
                decoded.append(symbol);

                tree.split(symbol);
                Node symNode = tree.getNode(symbol);
                tree.update(symNode, true);
            } else {
                decoded.append(symbol);
                tree.update(current);
            }
        }
        return decoded.toString();
    }
}
