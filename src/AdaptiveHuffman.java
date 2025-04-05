public class AdaptiveHuffman {
    private Tree tree;
    private Encoder encoder;
    private Decoder decoder;

    public AdaptiveHuffman() {
        this.tree = new Tree();
        this.encoder = new Encoder(tree);
        this.decoder = new Decoder(tree);
    }

    public String encode(String input) { // encode the input string
        return encoder.encode(input);
    }

    public String decode(String encoded) { // decode the encoded string
        this.tree = new Tree(); // reset the tree for decoding
        this.decoder = new Decoder(tree);
        return decoder.decode(encoded);
    }

    public String getStringCompression() { // get the compression trace
        return encoder.getStringCompression();
    }

    public Tree getTree() { // get the tree for visualization
        return tree;
    }

}