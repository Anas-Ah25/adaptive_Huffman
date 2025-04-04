public class AdaptiveHuffman {
    private String input;
    private String encodedString;
    private String decodedString;
    private Tree tree;

    public AdaptiveHuffman(String input) {
        this.input = input;
        this.encodedString = "";
        this.decodedString = "";
        this.tree = new Tree();
    }

