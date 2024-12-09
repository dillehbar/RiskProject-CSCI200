import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;


public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JButton button;
    private GameLayout gameLayout;
    private JLabel label;
    private String[] locationNames;
    private JButton button2;
    private HashMap<String, JButton> buttons = new HashMap<>();
    private static final int BUTTON_SIZE = 100;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    GridBagConstraints gbc = new GridBagConstraints();
    private JLabel playerTurnLabel;
    private Font largerFont;
    private static Boolean showTurn;

    public GUI(GameLayout gameLayout) {
        showTurn = false;
        this.gameLayout = gameLayout;
        frame = new JFrame("Main Game Driver");
        frame.setResizable(false);
        //panel = new JPanel();
        panel = new CustomPanel(buttons, gameLayout);
        panel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout

        // Create a larger font
        largerFont = new Font("Arial", Font.PLAIN, 24);

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

    public static int getScreenX() {
        return WINDOW_WIDTH;
    }
    public static int getScreenY() {
        return WINDOW_HEIGHT;
    }

    public static boolean showTurn() {
        return showTurn;
    }

    public void startNewGame()  {
    panel.removeAll();
    panel.revalidate();
    panel.repaint();
    System.out.println("Game started!");
    showTurn = true;
    try {
        gameLayout.loadFromFile("src/nodeConnectionsSTRAIGHTMAP.txt", "src/locations.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
       createSortedLocations();
        GameDriver.newGame(gameLayout);
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10, 10, 10, 10);
       //paintPlayerTurn();
       paintMap(gbc);
       gameLayout.setSelectedLocation(gameLayout.getCapital(1));
       paintSelected();
   }

    private void createSortedLocations() {
        // loop through all the location names, and sort them into an array
        locationNames = new String[gameLayout.getDescriptions().size()];
        int i = 0;
        for (String locationName : gameLayout.getDescriptions().keySet()) {
            locationNames[i] = locationName;
            i++;
        }
        Arrays.sort(locationNames);
        //System.out.println(Arrays.toString(locationNames));
    }


    public void loadGame(){
        // Load the game
        try {
            //GameDriver.loadGameState("gameState.ser");
            gameLayout= GameDriver.loadGameState("gameState.ser").getGameLayout();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        showTurn = true;
        // loop through all the location names, and sort them into an array
        createSortedLocations();
        panel.revalidate();
        panel.repaint();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;

        paintMap(gbc);
        update();
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
                if(gameLayout.isValidMove(loc.getName())){
                    gameLayout.setSelectedLocation(loc.getName());
                }
                panel.removeAll();
                paintMap(new GridBagConstraints());
            }
        });
        return button;
    }
    public void saveGameState(String filename) throws IOException {
        gameLayout.saveGameLayout(filename);
        System.out.println("Game state saved.");
    }
    private void paintPlayerTurn() {
        GridBagConstraints gbc = new GridBagConstraints();
        playerTurnLabel = new JLabel("Player " + gameLayout.getPlayerTurn() + "'s turn");
        playerTurnLabel.setFont(largerFont);
        panel.add(playerTurnLabel, gbc);
        gbc.gridy++;
    }

    private void paintMap(GridBagConstraints gbc) {
        int x = 0;
        int y = 0;
        gbc.gridx = x;
        gbc.gridy = y;
        panel.removeAll();
        if(locationNames == null){
            createSortedLocations();
        }
        for (String locationName : gameLayout.getDescriptions().keySet() /*locationNames*/) {

            JButton button = createImageButton("src/Assets/" + gameLayout.getDescriptions().get(locationName).getColor() + "Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get(locationName));
            button.setLayout(new BorderLayout());
            JLabel troopLabel = new JLabel(String.valueOf(gameLayout.getDescriptions().get(locationName).getTroopCount()));
            troopLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            JLabel locationLabel = new JLabel(locationName);
            locationLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            // add it above the troop label
            //panel.add(locationLabel, gbc);
            //panel.setComponentZOrder(locationLabel, 0);
            panel.add(troopLabel, gbc);
            panel.setComponentZOrder(troopLabel, 0);
            panel.add(button, gbc);
            buttons.put(locationName, button);

            gbc.gridy++;
            if (gbc.gridy > 2) { // Adjust this condition based on your layout requirements
                gbc.gridy = 0;
                gbc.gridx++;
            }
        }

        panel.revalidate();
        panel.repaint();
        paintGameButtons();
        paintSelected();
    }

    private void paintSelected() {

        if(gameLayout.getSelectedLocation() != null){
            JButton tempButton = buttons.get(gameLayout.getSelectedLocation());
            GridBagLayout layout = (GridBagLayout) panel.getLayout();
            GridBagConstraints originalGbc = layout.getConstraints(tempButton);
            tempButton = createImageButton("src/Assets/Selected"+gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getColor()+"Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()));
            System.out.println("src/Assets/Selected"+gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getColor()+"Node.png");
            panel.add(tempButton, originalGbc);
            JLabel troopLabel = new JLabel(String.valueOf(gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getTroopCount()));
            troopLabel.setFont(new Font("Arial", Font.PLAIN, 24));
            panel.add(troopLabel, originalGbc);
            panel.setComponentZOrder(troopLabel, 0);
            panel.setComponentZOrder(tempButton, 1);

            JButton button = buttons.get(gameLayout.getSelectedLocation());
            LocationDescription location = gameLayout.getDescriptions().get(gameLayout.getSelectedLocation());
            if (location.getOccupiedBy() == gameLayout.getRealPlayerTurn()) {
                for (String connectedLocation : gameLayout.getConnections(gameLayout.getSelectedLocation())) {
                    JButton connectedButton = buttons.get(connectedLocation);
                    if (connectedButton != null) {
                        JButton connectionButton = createImageButton("src/Assets/Valid" + gameLayout.getDescriptions().get(connectedLocation).getColor() + "Node.png", BUTTON_SIZE, BUTTON_SIZE, gameLayout.getDescriptions().get(connectedLocation));
                        GridBagLayout layout1 = (GridBagLayout) panel.getLayout();
                        GridBagConstraints originalGbc1 = layout1.getConstraints(connectedButton);
                        panel.add(connectionButton, originalGbc1);
                        JLabel troopLabel1 = new JLabel(String.valueOf(gameLayout.getDescriptions().get(connectedLocation).getTroopCount()));
                        troopLabel1.setFont(new Font("Arial", Font.PLAIN, 24));
                        panel.add(troopLabel1, originalGbc1);
                        panel.setComponentZOrder(troopLabel1, 0);
                        panel.setComponentZOrder(connectionButton, 1);
                    }
                }
            }


            panel.revalidate();
            panel.repaint();

        }

        // paint all valid connections with an image button using the ValidCOLORNode.png


    }
    private void paintGameButtons(){
        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveGameState("gameState.ser");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JButton searchForCapital = new JButton("Search for Capital");
        searchForCapital.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLayout.search(gameLayout.getSelectedLocation());
                panel.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                paintMap(gbc);
            }
        });
        JButton printAllLocations = new JButton("Print All Locations");
        printAllLocations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLayout.printAllLocations();
            }
        });
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLayout.nextTurn();
                panel.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                paintMap(gbc);
            }
        });
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                GUI gui = new GUI(gameLayout);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);

            }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(saveButton, gbc);
        gbc.gridx = 1;
        panel.add(searchForCapital, gbc);
        gbc.gridx = 2;
        panel.add(printAllLocations, gbc);
        gbc.gridx = 3;
        panel.add(endTurn, gbc);
        gbc.gridx = 4;
        panel.add(mainMenu, gbc);
        panel.revalidate();
        panel.repaint();
    }
    public Point getButtonLocation(JButton button) {
        return button.getLocation();
    }

    public void update() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        paintMap(gbc);
    }
}
