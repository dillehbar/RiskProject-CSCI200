import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class GUI {
    private JFrame frame;
    private CustomPanel panel;
    private JButton button;
    private GameLayout gameLayout;
    private JLabel label;
    private String[] locationNames;
    private JButton button2;
    private HashMap<String, JButton> buttons = new HashMap<>();
    private static final int BUTTON_SIZE = 100;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;
    private static final int SMALL_WINDOW_WIDTH = 500;
    private static final int SMALL_WINDOW_HEIGHT = 350;
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

    public static void gameWinnerPanel(GameLayout gameLayout) {
        JFrame newFrame = new JFrame("Game Over");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel label = new JLabel("Player "+gameLayout.getGameWinner()+" Wins!");
        panel.add(label, gbc);
        JButton button = new JButton("Close");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFrame.dispose();

            }
        });
        gbc.gridy++;
        panel.add(button, gbc);
        newFrame.add(panel);
        newFrame.pack();
        newFrame.setVisible(true);
    }

    public static void createPathPanel(List<String> path) {
        JFrame newFrame = new JFrame("Path");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridy = 0;
        gbc.gridx = 0;
        JLabel label = new JLabel("Path:");
        panel.add(label, gbc);
        for (int i = path.size() - 1; i >= 0; i--) {
            gbc.gridy++;
            JLabel locationLabel = new JLabel(path.get(i));
            panel.add(locationLabel, gbc);
        }
        newFrame.add(panel);
        newFrame.pack();
        newFrame.setVisible(true);
    }

    public void startNewGame()  {
    panel.removeAll();
    panel.revalidate();
    panel.repaint();
    System.out.println("Game started!");
    showTurn = true;
    try {
        gameLayout.loadFromFile(/*"src/nodeConnectionsSTRAIGHTMAP.txt"*/"src/nodeConnections2.txt", "src/locations.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
       createSortedLocations();
        GameDriver.newGame(gameLayout);
        gameLayout.setTroopsToPlace(gameLayout.countAllNewTroops(gameLayout.getRealPlayerTurn()));
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
    private void paintMap(GridBagConstraints gbc) {
        int x = 0;
        int y = 0;
        gbc.gridx = x;
        gbc.gridy = y;
        panel.removeAll();
        if(locationNames == null){
            createSortedLocations();
        }
        for (String locationName : /*gameLayout.getDescriptions().keySet()*/ locationNames) {

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
                // TODO: FIX THIS QUICK
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
                if(!gameLayout.checkGameWinner()){
                    gameLayout.nextTurn();
                    gameLayout.setTroopsToPlace(gameLayout.countAllNewTroops(gameLayout.getRealPlayerTurn()));
                    panel.removeAll();
                    if(gameLayout.getRealPlayerTurn() == 1){
                        gameLayout.setSelectedLocation(GameDriver.getPlayer1Location());
                    } else {
                        gameLayout.setSelectedLocation(GameDriver.getPlayer2Location());
                    }
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(10, 10, 10, 10);
                    update();
                } else {
                    gameWinnerPanel(gameLayout);
                }
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
        JButton showLocationDetails = new JButton("Show Location Details");
        showLocationDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLocationDetails(gameLayout.getSelectedLocation());
            }
        });
        JButton placeTroops = new JButton("Place Troops");
        placeTroops.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gameLayout.getPlayerTurn() == gameLayout.getRealPlayerTurn()){
                    if(gameLayout.getTroopsToPlace() > 0) {
                        placeTroops(gameLayout.getSelectedLocation());
                    } else {
                        JFrame newFrame = new JFrame("No More Troops");
                        JPanel newPanel = new JPanel();
                        newPanel.setPreferredSize(new Dimension(SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT));
                        newPanel.setLayout(new GridBagLayout());
                        JLabel label = new JLabel("You have no more troops to place.");
                        newPanel.add(label);
                        JButton okButton = new JButton("OK");
                        okButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                newFrame.dispose();
                            }
                        });
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.gridx = 0;
                        gbc.gridy = 1;
                        newPanel.add(okButton, gbc);
                        newFrame.add(newPanel);
                        newFrame.pack();
                        newFrame.setVisible(true);
                    }
                    panel.removeAll();
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(10, 10, 10, 10);
                    paintMap(gbc);
                }
                panel.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                paintMap(gbc);
            }
        });
        JButton attack = new JButton("Move Troops / Attack");
        attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveTroops();
                panel.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                paintMap(gbc);
            }
        });
        JButton forceWin = new JButton("Force Win");
        forceWin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // loop through all locations and set to controlled by player 1 and color blue
                for (String location : gameLayout.getDescriptions().keySet()) {
                    gameLayout.getDescriptions().get(location).setOccupiedBy(1);
                    gameLayout.getDescriptions().get(location).setColor("BLUE");
                }
                panel.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                paintMap(gbc);
            }
        });
        JButton exitGame = new JButton("Exit Game");
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
        gbc.gridx = 0;
        // add a small gap between the buttons
        gbc.gridy = 4;
        panel.add(new JLabel(" "), gbc);
        gbc.gridy = 5;
        panel.add(showLocationDetails, gbc);
        gbc.gridx = 1;
        if(gameLayout.getPlayerTurn() <= 2){
            panel.add(placeTroops, gbc);
        } else {
            panel.add(attack, gbc);
        }
        gbc.gridx = 2;
        panel.add(forceWin, gbc);
        gbc.gridx = 3;
        panel.add(exitGame, gbc);
        panel.updateButtons(buttons);
        panel.revalidate();
        panel.repaint();
    }

    private void moveTroops() {
        JFrame frame = new JFrame("Move Troops");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel label = new JLabel("Move troops from " + gameLayout.getSelectedLocation());
        panel.add(label, gbc);

        gbc.gridy++;
        JLabel label2 = new JLabel("To: ");
        panel.add(label2, gbc);

        gbc.gridy++;
        JComboBox<String> comboBox = new JComboBox<>();
        for (String connectedLocation : gameLayout.getConnections(gameLayout.getSelectedLocation())) {
            comboBox.addItem(connectedLocation);
        }
        panel.add(comboBox, gbc);

        gbc.gridy++;
        JSlider slider = new JSlider(1, gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getTroopCount());
        JLabel sliderLabel = new JLabel("Troops: " + slider.getValue() + "/" + gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getTroopCount());
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                sliderLabel.setText("Troops: " + slider.getValue() + "/" + gameLayout.getDescriptions().get(gameLayout.getSelectedLocation()).getTroopCount());
            }
        });
        panel.add(sliderLabel, gbc);
        gbc.gridy++;
        panel.add(slider, gbc);

        gbc.gridy++;
        JButton moveTroopsButton = new JButton("Move Troops");
        moveTroopsButton.setSize(100, 50);
        moveTroopsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLayout.moveTroopsToLocation(gameLayout.getSelectedLocation(), comboBox.getSelectedItem().toString(), slider.getValue());
                frame.dispose();
                update();
            }
        });
        panel.add(moveTroopsButton, gbc);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void placeTroops(String selectedLocation) {
    JFrame frame = new JFrame("Place Troops");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT));
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel label = new JLabel("Place troops in " + selectedLocation);
    panel.add(label, gbc);

    gbc.gridy++;
    JSlider slider = new JSlider(0, gameLayout.getTroopsToPlace());
    JLabel sliderLabel = new JLabel("Troops: " + slider.getValue() + "/" + gameLayout.getTroopsToPlace());
    slider.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            sliderLabel.setText("Troops: " + slider.getValue() + "/" + gameLayout.getTroopsToPlace());
        }
    });
    panel.add(sliderLabel, gbc);

    gbc.gridy++;
    panel.add(slider, gbc);

    gbc.gridy++;
    JButton placeTroopsButton = new JButton("Place Troops");
    placeTroopsButton.setSize(100, 50);
    placeTroopsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameLayout.placeTroops(selectedLocation, slider.getValue());
            frame.dispose();
            update();
        }
    });
    panel.add(placeTroopsButton, gbc);

    frame.add(panel);
    frame.pack();
    frame.setVisible(true);
}

    private void showLocationDetails(String selectedLocation) {
        LocationDescription location = gameLayout.getDescriptions().get(selectedLocation);
        JFrame frame = new JFrame("Location Details");
        // set frame size
        //frame.setSize(800, 600);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(SMALL_WINDOW_WIDTH, SMALL_WINDOW_HEIGHT));
        panel.setLayout(new GridLayout(0, 1));
        JLabel nameLabel = new JLabel("Name: " + location.getName());
        JLabel troopCountLabel = new JLabel("Troop Count: " + location.getTroopCount());
        JLabel occupiedByLabel = new JLabel("Occupied By: " + location.getOccupiedBy());
        JLabel isCapitalLabel = new JLabel("Is Capital: " + location.isCapital());
        JLabel colorLabel = new JLabel("Color: " + location.getColor());
        JLabel troopSpawnRateLabel = new JLabel("Troop Spawn Rate: " + location.getTroopSpawnRate());
        panel.add(nameLabel);
        panel.add(troopCountLabel);
        panel.add(occupiedByLabel);
        panel.add(isCapitalLabel);
        panel.add(colorLabel);
        panel.add(troopSpawnRateLabel);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
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
