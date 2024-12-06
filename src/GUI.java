import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private GameLayout gameLayout;
    private JLabel label;
    private JButton button2;
    private HashMap<String, JButton> buttons = new HashMap<>();
    private static final int BUTTON_SIZE = 100;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 500;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;

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
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10, 10, 10, 10);
       paintMap(gbc);
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


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
                gameLayout.setSelectedLocation(loc.getName());
                paintMap(new GridBagConstraints());
            }
        });
        return button;
    }
    public void saveGameState(String filename) throws IOException {
        gameLayout.saveGameLayout(filename);
        System.out.println("Game state saved.");
    }

    private void paintMap(GridBagConstraints gbc) {
        int x = 0;
        int y = 0;
        gbc.gridx = x;
        gbc.gridy = y;

        //saved(gbc, x, y);
        button = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A1"));
        button2 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A2"));
        button3 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A3"));
        button4 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B1"));
        button5 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B2"));
        button6 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B3"));
        button7 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C1"));
        button8 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C2"));
        button9 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C3"));
        button10 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D1"));
        button11 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D2"));
        button12 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D3"));
        button13 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E1"));
        button14 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E2"));
        button15 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E3"));
        panel.add(button, gbc);
        buttons.put("Territory A1", button);
        gbc.gridy++;
        panel.add(button2, gbc);
        buttons.put("Territory A2", button2);
        gbc.gridy++;
        panel.add(button3, gbc);
        buttons.put("Territory A3", button3);
        gbc.gridy = y;
        gbc.gridx++;
        panel.add(button4, gbc);
        buttons.put("Territory B1", button4);
        gbc.gridy++;
        panel.add(button5, gbc);
        buttons.put("Territory B2", button5);
        gbc.gridy++;
        panel.add(button6, gbc);
        buttons.put("Territory B3", button6);
        gbc.gridy = y;
        gbc.gridx++;
        panel.add(button7, gbc);
        buttons.put("Territory C1", button7);
        gbc.gridy++;
        panel.add(button8, gbc);
        buttons.put("Territory C2", button8);
        gbc.gridy++;
        panel.add(button9, gbc);
        buttons.put("Territory C3", button9);
        gbc.gridy = y;
        gbc.gridx++;
        panel.add(button10, gbc);
        buttons.put("Territory D1", button10);
        gbc.gridy++;
        panel.add(button11, gbc);
        buttons.put("Territory D2", button11);
        gbc.gridy++;
        panel.add(button12, gbc);
        buttons.put("Territory D3", button12);
        gbc.gridy = y;
        gbc.gridx++;
        panel.add(button13, gbc);
        buttons.put("Territory E1", button13);
        gbc.gridy++;
        panel.add(button14, gbc);
        buttons.put("Territory E2", button14);
        gbc.gridy++;
        panel.add(button15, gbc);
        buttons.put("Territory E3", button15);
        panel.revalidate();
        panel.repaint();
        paintSelected(gbc);


    }

    private void saved(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        JButton button = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A1"));
        panel.add(button, gbc);
        buttons.put("Territory A1", button);
        gbc.gridy++;
        JButton button2 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A2"));
        panel.add(button2, gbc);
        buttons.put("Territory A2", button2);
        gbc.gridy++;
        JButton button3 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory A3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory A3"));
        panel.add(button3, gbc);
        buttons.put("Territory A3", button3);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button4 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B1"));
        panel.add(button4, gbc);
        buttons.put("Territory B1", button4);
        gbc.gridy++;
        JButton button5 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B2"));
        panel.add(button5, gbc);
        buttons.put("Territory B2", button5);
        gbc.gridy++;
        JButton button6 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory B3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory B3"));
        panel.add(button6, gbc);
        buttons.put("Territory B3", button6);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button7 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C1"));
        panel.add(button7, gbc);
        buttons.put("Territory C1", button7);
        gbc.gridy++;
        JButton button8 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C2"));
        panel.add(button8, gbc);
        buttons.put("Territory C2", button8);
        gbc.gridy++;
        JButton button9 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory C3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory C3"));
        panel.add(button9, gbc);
        buttons.put("Territory C3", button9);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button10 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D1"));
        panel.add(button10, gbc);
        buttons.put("Territory D1", button10);
        gbc.gridy++;
        JButton button11 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D2").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D2"));
        panel.add(button11, gbc);
        buttons.put("Territory D2", button11);
        gbc.gridy++;
        JButton button12 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory D3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory D3"));
        panel.add(button12, gbc);
        buttons.put("Territory D3", button12);
        gbc.gridy = y;
        gbc.gridx++;
        JButton button13 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E1"));
        panel.add(button13, gbc);
        buttons.put("Territory E1", button13);
        gbc.gridy++;
        JButton button14 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E1").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E2"));
        panel.add(button14, gbc);
        buttons.put("Territory E2", button14);
        gbc.gridy++;
        JButton button15 = createImageButton("src/Assets/"+gameLayout.getDescriptions().get("Territory E3").getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get("Territory E3"));
        panel.add(button15, gbc);
        buttons.put("Territory E3", button15);
        //paintSelected();
        panel.revalidate();
        panel.repaint();
    }

    private void paintSelected(GridBagConstraints gbc) {
        if(gameLayout.getSelectedLocation() != null){
            JButton button = buttons.get(gameLayout.getSelectedLocation());
            int x = button.getX();
            int y = button.getY();
            gbc.gridy = y;
            gbc.gridx = x;
//          JButton selectedButton = createImageButton("src/Assets/Selected"+gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()));
//          panel.add(selectedButton, gbc);
            button = createImageButton("src/Assets/Selected"+gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()));
            System.out.println("src/Assets/Selected"+gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getColor()+"Node.png");
            // add the button on top of the existing button at the x and y coordinates
            panel.add(button, gbc);
            panel.revalidate();
            panel.repaint();

        }
    }
    public Point getButtonLocation(JButton button) {
        return button.getLocation();
    }
}
