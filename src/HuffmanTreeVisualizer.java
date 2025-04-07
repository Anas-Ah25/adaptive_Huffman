import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * HuffmanTreeVisualizer displays the adaptive Huffman tree.
 * Each node is drawn as a circle containing three lines:
 *   sym:  (the node’s symbol)
 *   cnt:  (the node’s counter)
 *   code: (the node’s binary code)
 *
 * The following color scheme is used:
 *   - Default: Node fill color teal, text color black.
 *   - Splitting node: Node highlighted in orange.
 *   - Nodes being swapped: Both nodes highlighted in red (then revert).
 *   - Counter update: Text turns green temporarily.
 *
 * Use the provided animateSplit(), animateSwap(), and animateCounterChange()
 * methods to trigger these temporary effects.
 */
public class HuffmanTreeVisualizer extends JFrame {

    // Public static instance for easy access from tree methods.
    public static HuffmanTreeVisualizer instance;

    // The adaptive Huffman tree to be visualized.
    private Tree tree;
    // Custom panel where the tree is drawn.
    private TreePanel treePanel;

    // Default colors
    private final Color DEFAULT_NODE_COLOR = new Color(0, 128, 128); // teal
    private final Color DEFAULT_TEXT_COLOR = Color.BLACK;
    private final Color SPLIT_COLOR = Color.ORANGE;
    private final Color SWAP_COLOR = Color.RED;
    private final Color COUNTER_COLOR = Color.GREEN;

    // Maps to keep track of node highlight states.
    private Map<Node, Color> nodeColors = new HashMap<>();
    private Map<Node, Color> textColors = new HashMap<>();

    public HuffmanTreeVisualizer(Tree tree) {
        this.tree = tree;
        instance = this;  // set static instance
        setTitle("Adaptive Huffman Tree Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        treePanel = new TreePanel();
        add(treePanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Call this method whenever the tree has been updated.
     */
    public void updateTree(Tree tree) {
        this.tree = tree;
        treePanel.repaint();
    }

    /**
     * Animate a split event. The given node will be highlighted in orange for a moment.
     */
    public void animateSplit(Node node) {
        nodeColors.put(node, SPLIT_COLOR);
        treePanel.repaint();
        new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nodeColors.put(node, DEFAULT_NODE_COLOR);
                treePanel.repaint();
            }
        }).start();
    }

    /**
     * Animate a swap event. Both nodes are painted in red for a moment, then revert.
     */
    public void animateSwap(Node node1, Node node2) {
        nodeColors.put(node1, SWAP_COLOR);
        nodeColors.put(node2, SWAP_COLOR);
        treePanel.repaint();
        new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nodeColors.put(node1, DEFAULT_NODE_COLOR);
                nodeColors.put(node2, DEFAULT_NODE_COLOR);
                treePanel.repaint();
            }
        }).start();
    }

    /**
     * Animate a counter change event.
     * The text color of the node changes to green temporarily.
     */
    public void animateCounterChange(Node node) {
        textColors.put(node, COUNTER_COLOR);
        treePanel.repaint();
        new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textColors.put(node, DEFAULT_TEXT_COLOR);
                treePanel.repaint();
            }
        }).start();
    }

    /**
     * Inner class for the panel that draws the tree.
     */
    private class TreePanel extends JPanel {
        private final int NODE_DIAMETER = 50;
        private final int VERTICAL_GAP = 80;
        private final int HORIZONTAL_GAP = 50;
        private final int MARGIN = 20;
        private int currentX;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (tree == null || tree.getRoot() == null) {
                return;
            }
            currentX = 0;
            Map<Node, Point> positions = new HashMap<>();
            computePositions(tree.getRoot(), 0, positions);
            drawConnections(g2d, tree.getRoot(), positions);
            drawNodes(g2d, tree.getRoot(), positions);
        }

        private void computePositions(Node node, int depth, Map<Node, Point> positions) {
            if (node == null) return;
            if (node.isLeaf()) {
                int x = MARGIN + currentX * (NODE_DIAMETER + HORIZONTAL_GAP);
                int y = MARGIN + depth * VERTICAL_GAP;
                positions.put(node, new Point(x, y));
                currentX++;
            } else {
                computePositions(node.getLeft(), depth + 1, positions);
                Point leftPos = positions.get(node.getLeft());
                Point rightPos = positions.get(node.getRight());
                int x;
                if (leftPos != null && rightPos != null) {
                    x = (leftPos.x + rightPos.x) / 2;
                } else if (leftPos != null) {
                    x = leftPos.x;
                } else if (rightPos != null) {
                    x = rightPos.x;
                } else {
                    x = MARGIN + currentX * (NODE_DIAMETER + HORIZONTAL_GAP);
                    currentX++;
                }
                int y = MARGIN + depth * VERTICAL_GAP;
                positions.put(node, new Point(x, y));
                computePositions(node.getRight(), depth + 1, positions);
            }
        }

        private void drawConnections(Graphics2D g2d, Node node, Map<Node, Point> positions) {
            if (node == null) return;
            Point p = positions.get(node);
            if (node.getLeft() != null) {
                Point leftP = positions.get(node.getLeft());
                g2d.drawLine(p.x + NODE_DIAMETER / 2, p.y + NODE_DIAMETER / 2,
                        leftP.x + NODE_DIAMETER / 2, leftP.y + NODE_DIAMETER / 2);
                drawConnections(g2d, node.getLeft(), positions);
            }
            if (node.getRight() != null) {
                Point rightP = positions.get(node.getRight());
                g2d.drawLine(p.x + NODE_DIAMETER / 2, p.y + NODE_DIAMETER / 2,
                        rightP.x + NODE_DIAMETER / 2, rightP.y + NODE_DIAMETER / 2);
                drawConnections(g2d, node.getRight(), positions);
            }
        }

        private void drawNodes(Graphics2D g2d, Node node, Map<Node, Point> positions) {
            if (node == null) return;
            Point p = positions.get(node);
            Color fillColor = nodeColors.getOrDefault(node, DEFAULT_NODE_COLOR);
            Color tColor = textColors.getOrDefault(node, DEFAULT_TEXT_COLOR);
            g2d.setColor(fillColor);
            g2d.fillOval(p.x, p.y, NODE_DIAMETER, NODE_DIAMETER);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x, p.y, NODE_DIAMETER, NODE_DIAMETER);

            String symbol = (node.getSymbol() != null) ? node.getSymbol() : "";
            String cnt = String.valueOf(node.getSymbolCount());
            String code = (node.getNodeBinCode() != null) ? node.getNodeBinCode() : "";
            String[] texts = { "sym:" + symbol, "cnt:" + cnt, "code:" + code };

            g2d.setColor(tColor);
            FontMetrics fm = g2d.getFontMetrics();
            int lineHeight = fm.getHeight();
            int totalTextHeight = texts.length * lineHeight;
            int startY = p.y + (NODE_DIAMETER - totalTextHeight) / 2 + fm.getAscent();
            for (int i = 0; i < texts.length; i++) {
                int textWidth = fm.stringWidth(texts[i]);
                int startX = p.x + (NODE_DIAMETER - textWidth) / 2;
                g2d.drawString(texts[i], startX, startY + i * lineHeight);
            }

            drawNodes(g2d, node.getLeft(), positions);
            drawNodes(g2d, node.getRight(), positions);
        }
    }

    public static void main(String[] args) {
        // Demonstration main (not used in the integrated app)
        Tree tree = new Tree();
        tree.split("A");
        Node nodeA = tree.getNode("A");
        Node nytNode = tree.getCurrentNTY();
        HuffmanTreeVisualizer visualizer = new HuffmanTreeVisualizer(tree);
        visualizer.animateSplit(nytNode);
        visualizer.animateCounterChange(nodeA);
        new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizer.animateSwap(nodeA, nytNode);
            }
        }).start();
    }
}
