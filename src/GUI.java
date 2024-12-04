import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private GameLayout gameLayout;
    private JLabel label;
    private JButton button2;

    public GUI(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        frame = new JFrame("Main Game Driver");
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(1000, 500));
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout

        // Create a larger font
        Font largerFont = new Font("Arial", Font.PLAIN, 24);

        label = new JLabel("Main Game Driver");
        label.setFont(largerFont); // Set the larger font for the label

        button = new JButton("New Game");
        button.setFont(largerFont);
        button2 = new JButton("Load Game");
        button2.setFont(largerFont);
        // Create GridBagConstraints for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0); // Add some padding
        gbc.anchor = GridBagConstraints.CENTER;

        panel.add(label, gbc);
        panel.add(button, gbc);
        panel.add(button2, gbc);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        button.addActionListener(e -> {
            try {
                this.startGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        button2.addActionListener(e -> this.loadGame());
    }

    public static void main(String[] args) {
        GameLayout gameLayout = new GameLayout();
        GUI gui = new GUI(gameLayout);
    }

    public void startGame() throws IOException, ClassNotFoundException {
        // Start a new game
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        JLabel label = new JLabel("New Game Started!");
        panel.add(label);
        panel.revalidate();
        panel.repaint();
        MainGameDriver.main(new String[0]);
    }

    public void loadGame() {
        // Load the game
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        JLabel label = new JLabel("Game Loaded!");
        panel.add(label);
        panel.revalidate();
        panel.repaint();

    }
}