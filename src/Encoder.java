public class Encoder {
    private Tree tree;
    private String encodedString; // final compressed data in binary
    private String stringCompression; // sting with the Nyt and ascii codes of the compressed string process

    public Encoder(Tree tree) {
        this.tree = tree;
        this.encodedString = "";
        this.stringCompression = "";
    }

    public String encode(String input) { // encode an entire string
        encodedString = ""; // reset for new encoding
        stringCompression = ""; // reset for new encoding
        for (char letter : input.toCharArray()) {
            String symbol = String.valueOf(letter);
            Node symbolNode = tree.getNode(symbol);
            if (symbolNode == null) { // if the symbol is not in the tree
                // send the nyt code, and the symbol short code
                String nytCode = tree.getCurrentNTY().getNodeBinCode();
                String shortCode = getShortCode(symbol);
                encodedString += nytCode + shortCode; // add to final compressed data
                stringCompression += "{ #newLetter# [ NYT:" + nytCode + " SymbASC:" + shortCode + "] }"; // to trace
                tree.split(symbol); // split the current nyt to two nodes
                symbolNode = tree.getNode(symbol); // get the new symbol node after split
            } else { // if the symbol is in the tree
                String symbolCode = symbolNode.getNodeBinCode(); // get the binary code according to its position now
                encodedString += symbolCode; // add to final compressed data
                stringCompression += "{ #old letter# [" + symbolCode + "]";

            }
            tree.update(symbolNode); // update the tree algorithm itself
        }
        return encodedString;
    }

    private void encodeChar(char input) { // encode a single character

    }

    private String getShortCode(String symbol) { // get ascii code of the symbol but as binary
        char c = symbol.charAt(0);
        return String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
    }

    public String getStringCompression() {
        return stringCompression;
    }

    public String getEncodedString() {
        return encodedString;
    }
}