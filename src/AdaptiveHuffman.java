public class AdaptiveHuffman {
    private Tree tree;
    private Encoder encoder;
    private Decoder decoder;

    public AdaptiveHuffman() {
        this.tree = new Tree();
        this.encoder = new Encoder(tree);
        this.decoder = new Decoder(tree);
    }

    public String encode(String input) {
        return encoder.encode(input);
    }

    public String decode(String encoded) {
        // Reset the tree for each decode so we start fresh
        this.tree = new Tree();
        this.decoder = new Decoder(tree);
        return decoder.decode(encoded);
    }

    public Tree getTree() {
        return tree;
    }

    public String getStringCompression() {
        return encoder.getStringCompression();
    }
}
