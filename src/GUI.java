import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private GameLayout gameLayout;
    private JLabel label;
    private JButton button2;
    private static final int BUTTON_SIZE = 60;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 500;

    public GUI(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
        frame = new JFrame("Main Game Driver");
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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
        button.addActionListener(e -> this.startNewGame());
        button2.addActionListener(e -> this.loadGame());
    }

    public static void main(String[] args) {
        GameLayout gameLayout = new GameLayout();
        GUI gui = new GUI(gameLayout);
    }

   public void startNewGame()  {
    panel.removeAll();
    panel.revalidate();
    panel.repaint();


    System.out.println("Game started!");
    try {
        gameLayout.loadFromFile("src/nodeConnections.txt", "src/locations.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
       paintMap();
   }



    public void loadGame() {
        // Load the game
        try {
            GameDriver.loadGameState("gameState.ser");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        JLabel label = new JLabel("Game Loaded!");
        panel.add(label);
        panel.revalidate();
        panel.repaint();


    }

    private JButton createImageButton(String imagePath, int width, int height, LocationDescription location) {
        JButton button = new JButton();
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(resizedImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.putClientProperty("location", location);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocationDescription loc = (LocationDescription) button.getClientProperty("location");
                System.out.println("Button clicked: " + loc.getName());
            }
        });
        return button;
    }
    public void saveGameState(String filename) throws IOException {
        gameLayout.saveGameLayout(filename);
        System.out.println("Game state saved.");
    }

    private void paintMap() {
        int x = 0;
        int y = 0;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = x;
        gbc.gridy = y;
        JButton button = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A1"));
        panel.add(button, gbc);
        gbc.gridy++;
        JButton button2 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A2"));
        panel.add(button2, gbc);
        gbc.gridy++;
        JButton button3 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A3"));
        panel.add(button3, gbc);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button4 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B1"));
        panel.add(button4, gbc);
        gbc.gridy++;
        JButton button5 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B2"));
        panel.add(button5, gbc);
        gbc.gridy++;
        JButton button6 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B3"));
        panel.add(button6, gbc);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button7 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C1"));
        panel.add(button7, gbc);
        gbc.gridy++;
        JButton button8 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C2"));
        panel.add(button8, gbc);
        gbc.gridy++;
        JButton button9 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C3"));
        panel.add(button9, gbc);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button10 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D1"));
        panel.add(button10, gbc);
        gbc.gridy++;
        JButton button11 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D2"));
        panel.add(button11, gbc);
        gbc.gridy++;
        JButton button12 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D3"));
        panel.add(button12, gbc);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button13 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E1"));
        panel.add(button13, gbc);
        gbc.gridy++;
        JButton button14 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E2"));
        panel.add(button14, gbc);
        gbc.gridy++;
        JButton button15 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E3"));
        panel.add(button15, gbc);
        panel.revalidate();
        panel.repaint();


//        for (LocationDescription location : gameLayout.getDescriptions().values()) {
//            JButton button = createImageButton("src/Assets/NeutralNode.png", BUTTON_SIZE, BUTTON_SIZE, location);
//            panel.add(button, gbc);
//            gbc.gridx++;
//            if (gbc.gridx >= 10) { // Adjust the number of columns as needed
//                gbc.gridx = 0;
//                gbc.gridy++;
//            }
//            System.out.println(location.getName());
//        }

    }
}
