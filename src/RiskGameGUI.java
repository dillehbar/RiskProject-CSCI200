import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

class RiskGameGUI extends JPanel {
    public RiskGameGUI() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Territories positions
        Point A1 = new Point(100, 100);
        Point A2 = new Point(100, 200);
        Point A3 = new Point(100, 300);
        Point B1 = new Point(200, 150);
        Point B2 = new Point(200, 250);
        Point B3 = new Point(200, 350);

        Point C1 = new Point(400, 100);
        Point C2 = new Point(400, 200);
        Point C3 = new Point(400, 300);

        Point D1 = new Point(600, 150);
        Point D2 = new Point(600, 250);
        Point D3 = new Point(600, 350);
        Point E1 = new Point(700, 100);
        Point E2 = new Point(700, 200);
        Point E3 = new Point(700, 300);

        // Draw territories
        drawTerritory(g2d, A1, "A1", Color.BLUE);
        drawTerritory(g2d, A2, "A2", Color.BLUE);
        drawTerritory(g2d, A3, "A3", Color.BLUE);
        drawTerritory(g2d, B1, "B1", Color.BLUE);
        drawTerritory(g2d, B2, "B2", Color.BLUE);
        drawTerritory(g2d, B3, "B3", Color.BLUE);

        drawTerritory(g2d, C1, "C1", Color.GRAY);
        drawTerritory(g2d, C2, "C2", Color.GRAY);
        drawTerritory(g2d, C3, "C3", Color.GRAY);

        drawTerritory(g2d, D1, "D1", Color.RED);
        drawTerritory(g2d, D2, "D2", Color.RED);
        drawTerritory(g2d, D3, "D3", Color.RED);
        drawTerritory(g2d, E1, "E1", Color.RED);
        drawTerritory(g2d, E2, "E2", Color.RED);
        drawTerritory(g2d, E3, "E3", Color.RED);

        /*
         * Draws All connections
         */
        drawConnection(g2d, A1, B1);
        drawConnection(g2d, A2, A1);
        drawConnection(g2d, A2, A3);
        drawConnection(g2d, A3, A2);
        drawConnection(g2d, B1, B3);
        drawConnection(g2d, B1, B2);
        drawConnection(g2d, B2, A2);
        drawConnection(g2d, B2, B1);
        drawConnection(g2d, B2, B3);
        drawConnection(g2d, B3, A3);
        drawConnection(g2d, B3, B2);
        drawConnection(g2d, B3, C3);
        drawConnection(g2d, C1, B1);
        drawConnection(g2d, C1, C2);
        drawConnection(g2d, C1, D1);
        drawConnection(g2d, C2, B2);
        drawConnection(g2d, C2, C1);
        drawConnection(g2d, C2, D2);
        drawConnection(g2d, C2, C3);
        drawConnection(g2d, C3, B3);
        drawConnection(g2d, C3, C2);
        drawConnection(g2d, C3, D3);
        drawConnection(g2d, D1, C1);
        drawConnection(g2d, D1, D2);
        drawConnection(g2d, D1, E1);
        drawConnection(g2d, D2, C2);
        drawConnection(g2d, D2, D1);
        drawConnection(g2d, D2, D3);
        drawConnection(g2d, D3, C3);
        drawConnection(g2d, D3, D2);
        drawConnection(g2d, E1, D1);
        drawConnection(g2d, E1, E2);
        drawConnection(g2d, E2, D2);
        drawConnection(g2d, E2, E1);
        drawConnection(g2d, E2, E3);
        drawConnection(g2d, E3, D3);
        drawConnection(g2d, E3, E2);
    }

    private void drawTerritory(Graphics2D g2d, Point location, String name, Color color) {
        g2d.setColor(color);
        g2d.fillOval(location.x - 15, location.y - 15, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(location.x - 15, location.y - 15, 30, 30);
        g2d.drawString(name, location.x - 10, location.y + 5);
    }

    private void drawConnection(Graphics2D g2d, Point from, Point to) {
        g2d.setColor(Color.BLACK);
        g2d.draw(new Line2D.Float(from.x, from.y, to.x, to.y));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Risk-Like Game GUI");
        RiskGameGUI panel = new RiskGameGUI();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}