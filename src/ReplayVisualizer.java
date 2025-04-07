import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ReplayVisualizer extends JFrame {

    private List<SnapshotEvent> events;
    private int currentIndex;
    private JPanel drawPanel;

    private final int NODE_DIAMETER = 50;
    private final int VERTICAL_GAP = 80;

    public ReplayVisualizer(List<SnapshotEvent> events) {
        this.events = events;
        currentIndex = 0;

        setTitle("Adaptive Huffman - Step by Step");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (events == null || events.isEmpty()) return;
                if (currentIndex < 0 || currentIndex >= events.size()) return;

                SnapshotEvent evt = events.get(currentIndex);
                TreeSnapshot snap = evt.snapshot;
                // Draw this snapshot
                drawSnapshot((Graphics2D) g, snap.root, getWidth() / 2, 50, getWidth() / 4, evt);
            }
        };
        add(drawPanel, BorderLayout.CENTER);

        JButton startBtn = new JButton("Start Replay");
        startBtn.addActionListener(e -> startReplay());
        add(startBtn, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startReplay() {
        if (events.size() == 0) return;
        currentIndex = 0;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                if (currentIndex >= events.size()) {
                    // Done all snapshots
                    timer.cancel();
                    // Stay on final snapshot
                    currentIndex = events.size() - 1;
                    drawPanel.repaint();
                    return;
                }
                drawPanel.repaint();
                currentIndex++;
            }
        }, 0, 1200); // 1.2 seconds per snapshot
    }

    /**
     * Draw the snapshot in a normal left-child layout:
     * left child => x - offset, right child => x + offset
     */
    private void drawSnapshot(Graphics2D g, NodeSnapshot node, int x, int y, int offset, SnapshotEvent evt) {
        if (node == null) return;

        // default colors
        Color fillColor = new Color(0, 128, 128); // teal
        Color textColor = Color.BLACK;

        boolean matchNode1 = (node.symbol.equals(evt.node1Symbol));
        boolean matchNode2 = (evt.node2Symbol != null && node.symbol.equals(evt.node2Symbol));

        // color logic:
        switch (evt.type) {
            case SPLIT:
                if (matchNode1) {
                    fillColor = Color.ORANGE; // highlight the parent node
                }
                break;
            case SWAP:
                // both swapped nodes are red
                if (matchNode1 || matchNode2) {
                    fillColor = Color.RED;
                }
                break;
            case COUNTER:
                if (matchNode1) {
                    textColor = Color.GREEN; // text in green
                }
                break;
        }

        // draw circle
        g.setColor(fillColor);
        g.fillOval(x - NODE_DIAMETER/2, y - NODE_DIAMETER/2, NODE_DIAMETER, NODE_DIAMETER);
        g.setColor(Color.BLACK);
        g.drawOval(x - NODE_DIAMETER/2, y - NODE_DIAMETER/2, NODE_DIAMETER, NODE_DIAMETER);

        // text lines: symbol, count, code
        String s1 = "sym:" + node.symbol;
        String s2 = "cnt:" + node.count;
        String s3 = "code:" + node.code;

        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int totalHeight = 3 * lineHeight;
        int startY = y - totalHeight/2 + fm.getAscent();
        String[] lines = { s1, s2, s3 };
        for (String line : lines) {
            int w = fm.stringWidth(line);
            g.drawString(line, x - w/2, startY);
            startY += lineHeight;
        }

        // edges + recursion
        int childY = y + VERTICAL_GAP;
        if (node.left != null) {
            g.drawLine(x, y, x - offset, childY);
            drawSnapshot(g, node.left, x - offset, childY, offset/2, evt);
        }
        if (node.right != null) {
            g.drawLine(x, y, x + offset, childY);
            drawSnapshot(g, node.right, x + offset, childY, offset/2, evt);
        }
    }
}
