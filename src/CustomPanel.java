import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CustomPanel extends JPanel implements Serializable {
    private HashMap<String, JButton> buttons;
    private GameLayout gameLayout;
    private boolean shownPanel = false;

    public CustomPanel(HashMap<String, JButton> buttons, GameLayout gameLayout) {
        this.buttons = buttons;
        this.gameLayout = gameLayout;
    }
    public void updateButtons(HashMap<String, JButton> buttons) {
        this.buttons = buttons;
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
                    g2d.drawLine(p1.x + button.getWidth() / 2, p1.y + button.getHeight() / 2,p2.x + connectedButton.getWidth() / 2, p2.y + connectedButton.getHeight() / 2);
                }
            }
        }

        // draw the labels above the buttons
        for (String location : buttons.keySet()) {
            JButton button = buttons.get(location);
            Point p = button.getLocation();
            g2d.drawString(location, p.x + button.getWidth() / 4, p.y );
        }

      // set custom font and print player turn
        if (GUI.showTurn()){
            if(gameLayout.checkGameWinner()){
                g2d.setFont(new Font("Arial", Font.BOLD, 25));
                g2d.drawString("Player " + gameLayout.getGameWinner() + " Wins!", (GUI.getScreenX() / 2) - 80, 50);

                while(!shownPanel){
                    GUI.gameWinnerPanel(gameLayout);
                    shownPanel = true;
                }
            } else {
                g2d.setFont(new Font("Arial", Font.BOLD, 25));
                g2d.drawString("Player " + gameLayout.getRealPlayerTurn() + "'s Turn", (GUI.getScreenX() / 2) - 80, 50);
                if(gameLayout.getPlayerTurn() <= 2){
                    g2d.drawString("Troops to place: " + gameLayout.getTroopsToPlace(), (GUI.getScreenX() / 2) - 80, 80);
                } else  {
                    g2d.drawString("Move/Attack Phase", (GUI.getScreenX() / 2) - 120, 80);
                }
            }

        }

    }
}