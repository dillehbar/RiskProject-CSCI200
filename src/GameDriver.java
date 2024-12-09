import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class GameDriver {
    private GameLayout gameLayout;
    private static String selectedLocation;
    private static String player1Location;
    private static String player2Location;
    private static final long serialVersionUID = 1L;

    public GameDriver(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
    }

    public static void newGame(GameLayout gameLayout) {
        int randomNum = (int) (Math.random() * 3) + 1;
        int randomNum2 = (int) (Math.random() * 3) + 1;
        gameLayout.setCapital("Territory A" + randomNum);
        gameLayout.setCapital("Territory A" + randomNum);
        gameLayout.getLocationDescription("Territory A" + randomNum).addTroopCount(5);
        setSelectedLocation("Territory A" + randomNum);
        setPlayer1Location("Territory A" + randomNum);
        gameLayout.setCapital("Territory E" + randomNum2);
        gameLayout.getLocationDescription("Territory E" + randomNum2).addTroopCount(5);
        System.out.println("Game started.");
    }

    public GameLayout getGameLayout() {
        return this.gameLayout;
    }

    public void listAllLocations() {
        Iterator<String> iterator = gameLayout.getLocationIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void listSelectedLocationProperties() {
        LocationDescription description = gameLayout.getLocationDescription(selectedLocation);
        System.out.println("Location: " + description.getName());
        System.out.println("Troop Spawn Rate: " + description.getTroopSpawnRate());
        System.out.println("Troop Count: " + description.getTroopCount());
        System.out.println("Occupied By: " + description.getOccupiedBy());
        System.out.println("Is Capital: " + description.getIsCapital());
        System.out.println("Color: " + description.getColor());
    }

    public void listConnectedLocations() {
        System.out.println("Current Location: " + selectedLocation);
        Iterator<String> iterator = gameLayout.getConnectionsIterator(selectedLocation);
        if (!iterator.hasNext()) {
            System.out.println("No connections found.");
            return;
        }
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void moveToLocation(String newLocation) {
        Iterator<String> iterator = gameLayout.getConnectionsIterator(selectedLocation);
        boolean isConnected = false;
        while (iterator.hasNext()) {
            if (iterator.next().equals(newLocation)) {
                isConnected = true;
                break;
            }
        }
        if (isConnected) {
            //selectedLocation.setNotCurrentNode();
            selectedLocation = newLocation;
            //selectedLocation.setCurrentNode();
            System.out.println("Moved to: " + selectedLocation);
        } else {
            System.out.println("Invalid move. Location not connected.");
        }
    }

    //    public void saveGameState(String connectionsFile, String descriptionsFile) throws FileNotFoundException {
//        gameLayout.saveToFile(connectionsFile, descriptionsFile);
//        System.out.println("Game state saved.");
//    }

    /**
     * Save the current game state to a file
     * @param filename the name of the file to save the game state to
     * @throws IOException if the file cannot be written to
     */
    public void saveGameState(String filename) throws IOException {
        gameLayout.saveGameLayout(filename);
        System.out.println("Game state saved.");
    }

    /**
     * Save the current game state to a file
     * @param filename the name of the file to save the game state to
     * @return the game driver object
     * @throws IOException if the file cannot be written to
     * @throws ClassNotFoundException if the class of the object cannot be found
     */
    public static GameDriver loadGameState(String filename) throws IOException, ClassNotFoundException {
        GameLayout gameLayout = GameLayout.loadGameLayout(filename);
        return new GameDriver(gameLayout);
    }

    // method to return all location controlled by the user

    /**
     * List all locations controlled by a player
     * @param player the player to list the locations for
     */
    public void listControlledLocations(int player) {
        Iterator<String> iterator = gameLayout.getLocationIterator();
        while (iterator.hasNext()) {
            LocationDescription description = gameLayout.getLocationDescription(iterator.next());
            if (description.getOccupiedBy() == player) {
                System.out.println(description.getName());
            }
        }
    }
    /**
     * List all locations controlled by a player
     * @param player the player to list the locations for
     */
    public void listControlledLocationsAndTroopCount(int player) {
        Iterator<String> iterator = gameLayout.getLocationIterator();
        while (iterator.hasNext()) {
            LocationDescription description = gameLayout.getLocationDescription(iterator.next());
            if (description.getOccupiedBy() == player) {
                System.out.println(description.getName() + " " + description.getTroopCount());
            }
        }
    }

    /**
     * Get the selected location
     * @param location the location to set as selected
     */
    public static void setSelectedLocation(String location) {
        selectedLocation = location;
    }

    //method to add the spawn rate of all controlled locations

//    public int getSpawnedTroops(int player) {
//        int total = 0;
//        Iterator<String> iterator = gameLayout.getLocationIterator();
//        while (iterator.hasNext()) {
//            LocationDescription description = gameLayout.getLocationDescription(iterator.next());
//            if (description.getOccupiedBy() == player) {
//                total += description.getTroopSpawnRate();
//            }
//        }
//        return total;
//    }
    public static void setPlayer1Location(String location) {
        player1Location = location;
    }
    public static void setPlayer2Location(String location) {
        player2Location = location;
    }
    public static String getPlayer1Location() {
        return player1Location;
    }
    public static String getPlayer2Location() {
        return player2Location;
    }
    public static String getSelectedLocation() {
        return selectedLocation;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        GameLayout gameLayout = new GameLayout();
        GameDriver gameDriver = new GameDriver(gameLayout);
        GUI gui = new GUI(gameLayout);



    }

}

