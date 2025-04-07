public class Encoder {
    private Tree tree;
    private String encodedString;
    private String stringCompression;

    public Encoder(Tree tree) {
        this.tree = tree;
        this.encodedString = "";
        this.stringCompression = "";
    }

    public String encode(String input) {
        encodedString = "";
        stringCompression = "";

        for (char letter : input.toCharArray()) {
            String symbol = String.valueOf(letter);
            Node symbolNode = tree.getNode(symbol);
            boolean isNewSymbol = false;
            if (symbolNode == null) {
                String nytCode = tree.getCurrentNTY().getNodeBinCode();
                String shortCode = getShortCode(symbol);
                encodedString += (nytCode + shortCode);
                stringCompression += "{#newLetter# [NYT:" + nytCode + " ASC:" + shortCode + "]}";

                tree.split(symbol);
                symbolNode = tree.getNode(symbol);
                isNewSymbol = true;
            } else {
                String code = symbolNode.getNodeBinCode();
                encodedString += code;
                stringCompression += "{#oldLetter# [" + code + "]}";
            }
            tree.update(symbolNode, isNewSymbol);
        }
        return encodedString;
    }

    private String getShortCode(String symbol) {
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
