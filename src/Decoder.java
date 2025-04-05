public class Decoder {
    private Tree tree;

    public Decoder(Tree tree) {
        this.tree = tree;
    }



    private String readCode(String shortCode) { // convert ascii to symbol
        int ascii = Integer.parseInt(shortCode, 2);
        return String.valueOf((char) ascii);
    }
    public String decode(String encoded) { // decode the binary string
        StringBuilder decoded = new StringBuilder();
        int i = 0;
        while (i < encoded.length()) {
            Node current = tree.getRoot();
            while (!current.isLeaf()) { // traverse until reaching a leaf
                if (i >= encoded.length()) {
                    break;
                }
                char bit = encoded.charAt(i++);
                if (bit == '0') {
                    current = current.getLeft();
                } else {
                    current = current.getRight();
                }
            }


            if (current.getSymbol().equals("NYT")) { // new symbol
                if (i + 8 > encoded.length()){
                    break;
                }
                String shortCode = encoded.substring(i,i+8); // read ascii code for 1 symbol
                i += 8;
                String symbol = readCode(shortCode);
                decoded.append(symbol);
                tree.split(symbol); // split the NYT node
                Node symbolNode = tree.getNode(symbol);
                tree.update(symbolNode); // update the tree
            } else { // existing symbol
                String symbol = current.getSymbol();
                decoded.append(symbol);
                tree.update(current); // update the tree
            }
        }
        return decoded.toString();
    }


}