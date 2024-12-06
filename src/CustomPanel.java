import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CustomPanel extends JPanel {
    private HashMap<String, JButton> buttons;
    private GameLayout gameLayout;

    public CustomPanel(HashMap<String, JButton> buttons, GameLayout gameLayout) {
        this.buttons = buttons;
        this.gameLayout = gameLayout;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);

        for (String location : buttons.keySet()) {
            JButton button = buttons.get(location);
            Point p1 = button.getLocation();
            List<String> connections = gameLayout.getConnections(location);
            for (String connectedLocation : connections) {
                JButton connectedButton = buttons.get(connectedLocation);
                if (connectedButton != null) {
                    Point p2 = connectedButton.getLocation();
                    g2d.drawLine(p1.x + button.getWidth() / 2, p1.y + button.getHeight() / 2,
                            p2.x + connectedButton.getWidth() / 2, p2.y + connectedButton.getHeight() / 2);
                }
            }
        }
    }
}