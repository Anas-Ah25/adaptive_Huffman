import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest {
    private AdaptiveHuffman huffman;
    private Tree tree;
    private Encoder encoder;
    private Decoder decoder;

    @Before
    public void setUp() {
        // Initialize test data before each test
        huffman = new AdaptiveHuffman();
        tree = huffman.getTree();
        encoder = new Encoder(tree);
        decoder = new Decoder(tree);
    }

    @Test
    public void testTreeInitialization() {
        // Test that tree initializes with NYT node correctly
        assertNotNull(tree.getRoot());
        assertEquals("NYT", tree.getRoot().getSymbol());
        assertEquals("", tree.getRoot().getNodeBinCode());
        assertEquals(0, tree.getRoot().getSymbolCount());
    }

    @Test
    public void testNodeCreation() {
        // Test node creation and properties
        Node node = new Node("a", 99);
        assertEquals("a", node.getSymbol());
        assertEquals(99, node.getNodeNumber());
        assertEquals(0, node.getSymbolCount());
        assertTrue(node.isLeaf());
        assertTrue(node.isRoot()); // A newly created node has no parent, so it's a root
    }

    @Test
    public void testTreeSplit() {
        // Test splitting NYT node with a new symbol
        tree.split("a");

        // Check old NYT node (now internal)
        assertNull(tree.getRoot().getSymbol());
        assertEquals(1, tree.getRoot().getSymbolCount());

        // Check new NYT node
        assertEquals("NYT", tree.getRoot().getLeft().getSymbol());
        assertEquals(0, tree.getRoot().getLeft().getSymbolCount());

        // Check symbol node
        assertEquals("a", tree.getRoot().getRight().getSymbol());
        assertEquals(1, tree.getRoot().getRight().getSymbolCount());

        // Check binary codes
        assertEquals("0", tree.getRoot().getLeft().getNodeBinCode());
        assertEquals("1", tree.getRoot().getRight().getNodeBinCode());
    }

    @Test
    public void testNodeSearch() {
        // Test searching for nodes in the tree
        tree.split("a");
        tree.split("b");

        Node aNode = tree.getNode("a");
        Node bNode = tree.getNode("b");
        Node nonexistent = tree.getNode("x");

        assertNotNull(aNode);
        assertNotNull(bNode);
        assertNull(nonexistent);

        assertEquals("a", aNode.getSymbol());
        assertEquals("b", bNode.getSymbol());
    }

    @Test
    public void testEncoderBasic() {
        // Test basic encoding functionality
        String input = "aab";
        huffman.encode(input); // Use AdaptiveHuffman to encode

        // Tree should have split for 'a' and updated counts
        Node aNode = tree.getNode("a");
        assertNotNull(aNode);
        assertEquals(2, aNode.getSymbolCount()); // 'a' appears twice
    }

    @Test
    public void testBinaryCodeUpdate() {
        // Test binary code generation
        Node node = new Node("test", 99);
        Node parent = new Node("parent", 100);
        node.setParent(parent);
        parent.setLeft(node);

        node.updateBinaryCode();
        assertEquals("0", node.getNodeBinCode());

        parent.setLeft(null);
        parent.setRight(node);
        node.updateBinaryCode();
        assertEquals("1", node.getNodeBinCode());
    }

    @Test
    public void testSymbolCountUpdate() {
        // Test symbol count incrementing
        String input = "aab";
        huffman.encode(input); // Use AdaptiveHuffman to encode

        Node aNode = tree.getNode("a");
        Node bNode = tree.getNode("b");

        assertEquals(2, aNode.getSymbolCount()); // 'a' appears twice, should be incremented in update
        assertEquals(1, bNode.getSymbolCount()); // 'b' appears once
    }

    @Test
    public void testParentChildRelationship() {
        // Test parent-child relationships after split
        tree.split("a");

        Node root = tree.getRoot();
        Node nyt = tree.getCurrentNTY();
        Node aNode = tree.getNode("a");

        assertEquals(root, nyt.getParent());
        assertEquals(root, aNode.getParent());
        assertEquals(nyt, root.getLeft());
        assertEquals(aNode, root.getRight());
    }

    @Test
    public void testBinaryCodeUpdateAfterSwap() {
        // Test binary code updates after a swap
        String input = "AAB"; // Encode a string to trigger updates
        huffman.encode(input);

        Node aNode = tree.getNode("A");
        Node bNode = tree.getNode("B");

        // After encoding "AAB":
        // 'A' (first): split sets count to 1, update skips increment (isNewSymbol=true)
        // 'A' (second): update increments count to 2 (isNewSymbol=false)
        // 'B': split sets count to 1
        assertEquals(2, aNode.getSymbolCount());
        assertEquals(1, bNode.getSymbolCount());

        // Binary codes should reflect the tree structure after updates
        tree.updateNodeCodes(tree.getRoot());
        assertNotNull(aNode.getNodeBinCode());
        assertNotNull(bNode.getNodeBinCode());
    }
}