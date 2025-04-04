import java.util.Map;

public class Encoder {
    private String input;
    private String encodedString;
    private Tree tree;

    public Encoder(String input, Map<String, Integer> symbolsShortCode){
        this.input = input;
        this.encodedString = "";
        this.stringCompression = ""; // sting with the Nyt and ascii codes of the compressed string process
    }

    // map is used to get  the short code of the symbols to use in case of first time

    public void encode() {
        for (char c : input.toCharArray()) {
            String symbol = String.valueOf(c);
            if (tree.getNode(symbol) == null) { // if the symbol is not in the tree
                // send the nyt code, and the symbol short code
//                tree.split(symbol, tree.getCurrentNTY());
                encodedString += tree.getCurrentNTY(); // add the nyt code
                encodedString += symbolsShortCode.get(symbol); // add the symbol short code
                updateTree(symbol); // update the tree algorithm
            } else { // if the symbol is in the tree
                encodedString += tree.getNode(symbol).getNodeBinCode();
            }
            tree.updateSymbolCount(symbol);
        }
        System.out.println(encodedString); // binary string
    }




}