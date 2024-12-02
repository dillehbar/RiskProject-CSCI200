import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
// TODO FiX File save and load
// TODO Add GUI
// TODO MAYBE GET RID OF RANDOM CAPITALS
public class MainGameDriver {
    private GameLayout gameLayout;
    private static String selectedLocation;

    public MainGameDriver(GameLayout gameLayout) {
        this.gameLayout = gameLayout;
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
    public static MainGameDriver loadGameState(String filename) throws IOException, ClassNotFoundException {
        GameLayout gameLayout = GameLayout.loadGameLayout(filename);
        return new MainGameDriver(gameLayout);
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


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        GameLayout gameLayout = new GameLayout();
        MainGameDriver gameDriver = new MainGameDriver(gameLayout);

        while (true) {
            System.out.println("Choose an action: \n1. Start New Game\n2. Load Saved Game");
            String answer = scanner.nextLine();

            if (answer.equals("2")) {
                gameDriver = loadGameState("gameState.ser");
                System.out.println("Game continued.");
                //gameLayout.loadFromFile("src/connectionsSaved.txt", "src/descriptionsSaved.txt");
                /* Not planning on having a true starting location unless needed */
                //gameDriver = new GameDriver(gameLayout, "Territory A1");
                break;
            } else if (answer.equals("1")) {
                int randomNum = (int) (Math.random() * 3) + 1;
                int randomNum2 = (int) (Math.random() * 3) + 1;
                gameLayout.loadFromFile("src/nodeConnections.txt", "src/locations.txt");
                gameLayout.setCapital("Territory A" + randomNum);
                gameLayout.getLocationDescription("Territory A" + randomNum).addTroopCount(5);
                setSelectedLocation("Territory A" + randomNum);
                gameLayout.setCapital("Territory E" + randomNum2);
                gameLayout.getLocationDescription("Territory E" + randomNum2).addTroopCount(5);
                gameDriver = new MainGameDriver(gameLayout);
                System.out.println("Game started.");
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }

        //game loop

        System.out.println("Capital location: " + selectedLocation);
        gameDriver.listSelectedLocationProperties();
        while (true){
            /* Game Logic
            Game Begins,
            Players get to place initial troops, game begins
             * * (AFTER EVERY EVENT) Check if a player has won
             *  if(getPlayerTurn == 1) --> have player 1 place their troops that spawn
             *  if(getPlayerTurn == 2) --> have player 2 place their troops that spawn
             *  if(getPlayerTurn == 3) --> have player 1 attack or move troops
             *  if(getPlayerTurn == 4) --> have player 2 attack or move troops
             *
             * if # % 2 is 1 0, then player 1
             */
            if(gameLayout.getGameWinner() != 0){
                System.out.println("Player " + gameLayout.getGameWinner() + " has won!");
                break;
            }
            if(gameLayout.getPlayerTurn() % 2 == 1){
                System.out.println("Player 1's turn");
            } else {
                System.out.println("Player 2's turn");
            }
            if(gameLayout.getPlayerTurn() == 1){
                System.out.println("Player 1's turn");
                int placeableTroops = gameLayout.countAllNewTroops();
                gameDriver.listControlledLocations(1);
                while(placeableTroops > 0){
                    System.out.println("Choose a location to place troops: ");
                    String location = scanner.nextLine();
                    System.out.println("Enter the number of troops to place: ");
                    int troops = scanner.nextInt();
                    if(troops > placeableTroops){
                        System.out.println("Invalid number of troops. Please try again.");
                        continue;
                    }
                    gameLayout.getLocationDescription(location).addTroopCount(troops);
                    placeableTroops -= troops;
                }
                gameLayout.nextTurn();
            }


        }


    }
}

