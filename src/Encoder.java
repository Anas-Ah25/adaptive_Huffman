public class Encoder {
    private Tree tree;
    private String encodedString; // final compressed data in binary
    private String stringCompression; // sting with the Nyt and ascii codes of the compressed string process

    public Encoder(Tree tree) {
        this.tree = tree;
        this.encodedString = "";
        this.stringCompression = "";
    }

    public String encode(char input) {
        encodedString = ""; // reset for new encoding
        stringCompression = ""; // reset for new encoding
        String symbol = String.valueOf(input);
        Node symbolNode = tree.getNode(symbol);
        if (symbolNode == null) { // if the symbol is not in the tree
            // send the nyt code, and the symbol short code
            String nytCode = tree.getCurrentNTY().getNodeBinCode();
            String shortCode = getShortCode(input);
            encodedString += nytCode + shortCode; // add to final compressed data
            stringCompression += "{ NYT:" + nytCode + "SymbASC:" + shortCode + " }"; // to trace
//        tree.split(symbol);
//        symbolNode = tree.getNode(symbol);

        /* need to make a function for the tree algorithm, as a blackbox to use in both the conditions here */
        } else { // if the symbol is in the tree
            String huffmanCode = symbolNode.getNodeBinCode();
            encodedString += huffmanCode; // add to final compressed data
        }
        tree.update(symbolNode); // update the tree algorithm itself
        return encodedString;
    }

    private String getShortCode(char symbol) { // get ascii code of the symbol but as binary
        return String.format("%8s", Integer.toBinaryString(symbol)).replace(' ', '0');
    }

    public String getStringCompression() {
        return stringCompression;
    }

    public String getEncodedString() {
        return encodedString;
    }

}
    // map is used to get  the short code of the symbols to use in case of first time

//    public void encode() {
//        for (char c : input.toCharArray()) {
//            String symbol = String.valueOf(c);
//            if (tree.getNode(symbol) == null) { // if the symbol is not in the tree
//                // send the nyt code, and the symbol short code
////                tree.split(symbol, tree.getCurrentNTY());
//                encodedString += tree.getCurrentNTY(); // add the nyt code
//                encodedString += symbolsShortCode.get(symbol); // add the symbol short code
//                updateTree(symbol); // update the tree algorithm
//            } else { // if the symbol is in the tree
//                encodedString += tree.getNode(symbol).getNodeBinCode();
//            }
//            tree.updateSymbolCount(symbol);
//        }
//        System.out.println(encodedString); // binary string
//    }
//
//
//

