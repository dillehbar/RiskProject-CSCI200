import java.io.*;
import java.util.*;

public class GameLayout implements Serializable {
    /**
     * The connections between the locations
     */
    private HashMap<String, Set<String>> connections;
    /**
     * The descriptions of the locations
     */
    private HashMap<String, LocationDescription> descriptions;
    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * The current player turn
     */
    private int playerTurn;
    /**
     * The game winner
     */
    private int gameWinner = 0;
    /**
     * Creates a new GameLayout object
     */
    public GameLayout() {
        connections = new HashMap<>();
        descriptions = new HashMap<>();
        playerTurn = 1;
    }


    /**
     * Loads the game layout from a file
     * @param connectionsFile The name of the file containing the connections
     * @param descriptionsFile The name of the file containing the descriptions
     * @throws FileNotFoundException If the file is not found
     */
    public void loadFromFile(String connectionsFile, String descriptionsFile) throws FileNotFoundException {
        loadDescriptions(descriptionsFile);
        loadConnections(connectionsFile);
    }

    /**
     * Sets the capital location
     * @param location The location
     */
    public void setCapital(String location) {
//        for (LocationDescription description : descriptions.values()) {
//            description.setIsCapital(false);
//        }
        descriptions.get(location).setIsCapital(true);
    }
    public String getCapital(int player) {
        for (String location : descriptions.keySet()) {
            if (descriptions.get(location).getIsCapital() && descriptions.get(location).getOccupiedBy() == player) {
                return location;
            }
        }
        return null;
    }

    /**
     * Loads the connections between the locations from a file
     * @param connectionsFile The name of the file
     * @throws FileNotFoundException If the file is not found
     */
    private void loadConnections(String connectionsFile) throws FileNotFoundException {
    try (BufferedReader reader = new BufferedReader(new FileReader(connectionsFile))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Read the location
            String location = line.trim();
            if (location.isEmpty()) {
                continue; // Skip empty lines
            }

            // Read the number of connections
            line = reader.readLine();
            if (line == null) {
                throw new IllegalArgumentException("Expected number of connections for location: " + location);
            }
            int numConnections;
            try {
                numConnections = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format for connections at location: " + location, e);
            }

            // Read the connected locations
            Set<String> connectedLocations = new HashSet<>();
            for (int i = 0; i < numConnections; i++) {
                line = reader.readLine();
                if (line == null) {
                    throw new IllegalArgumentException("Expected connected location for location: " + location);
                }
                connectedLocations.add(line.trim());
            }

            // Add the location and its connections to the map
            connections.put(location, connectedLocations);
            // Skip the empty line between entries
            reader.readLine();
        }
    } catch (FileNotFoundException e) {
        throw new FileNotFoundException("File not found: " + connectionsFile);
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
}

    /**
     * Loads the descriptions of the locations from a file
     * @param descriptionsFile The name of the file
     * @throws FileNotFoundException If the file is not found
     */
    private void loadDescriptions(String descriptionsFile) throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(descriptionsFile))) {
        while (scanner.hasNextLine()) {
            // Read the name
            String name = scanner.nextLine().trim();

            // Read the properties
            int troopSpawnRate = Integer.parseInt(scanner.nextLine().trim());
            int troopCount = Integer.parseInt(scanner.nextLine().trim());
            int occupiedBy = Integer.parseInt(scanner.nextLine().trim());
            boolean isCapital = Boolean.parseBoolean(scanner.nextLine().trim());
            String color = scanner.nextLine().trim();
            //boolean isCurrentNode = Boolean.parseBoolean(scanner.nextLine().trim());

            // Add the description to the map
            descriptions.put(name, new LocationDescription(name, troopSpawnRate, troopCount, occupiedBy, isCapital, color/*, isCurrentNode*/));

            // Skip the empty line between entries
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        }
    } catch (NumberFormatException e) {
        System.err.println("Error (loadDescriptions): Malformed number in file. Please check the input file format.");
    } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());
    }
}

    /**
     * Returns an iterator for the locations
     * @return
     */
    public Iterator<String> getLocationIterator() {
        return descriptions.keySet().iterator();
    }

    /**
     * Returns an iterator for the connections of the given location
     * @param location The location
     * @return Iterator<String> The iterator for the connections
     */
    public Iterator<String> getConnectionsIterator(String location) {
    if (connections.containsKey(location)) {
        return connections.get(location).iterator();
    }
    return null;
}

    /**
     * Returns the location description for the given location
     * @param location The location
     * @return LocationDescription
     */
    public LocationDescription getLocationDescription(String location) {
        return descriptions.get(location);
    }


//    public void updateLocationDescription(String location, LocationDescription newDescription) {
//        descriptions.put(location, newDescription);
//    }
//
//    public void saveToFile(String connectionsFile, String descriptionsFile) throws FileNotFoundException {
//        saveConnections(connectionsFile);
//        saveDescriptions(descriptionsFile);
//    }
//
//    private void saveConnections(String connectionsFile) throws FileNotFoundException {
//        // first print the location, new line then the number of connections, new line then the connections
//        try (PrintWriter writer = new PrintWriter(new File(connectionsFile))) {
//            for (Map.Entry<String, Set<String>> entry : connections.entrySet()) {
//                writer.println(entry.getKey());
//                writer.println(entry.getValue().size());
//                for (String connectedLocation : entry.getValue()) {
//                    writer.println(connectedLocation);
//                }
//                writer.println();
//            }
//        }
//    }
//
//    private void saveDescriptions(String descriptionsFile) throws FileNotFoundException {
//        try (PrintWriter writer = new PrintWriter(new File(descriptionsFile))) {
//            for (LocationDescription description : descriptions.values()) {
//                writer.println(description.getName());
//                writer.println(description.getTroopSpawnRate());
//                writer.println(description.getTroopCount());
//                writer.println(description.getOccupiedBy());
//                writer.println(description.getIsCapital());
//                writer.println(description.getColor());
//                writer.println();
//            }
//        }
//    }

    /**
     * Saves the game layout to a file
     * @param filename The name of the file
     * @throws IOException If an I/O error occurs
     */
    public void saveGameLayout(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }
    /**
     * Loads a game layout from a file
     * @param filename The name of the file
     * @return GameLayout The game layout
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If the class of the object in the file cannot be found
     */
    public static GameLayout loadGameLayout(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            GameLayout gameLayout = (GameLayout) ois.readObject();
            // Debug statement to verify the contents of descriptions
            System.out.println("Descriptions after loading:");
            for (Map.Entry<String, LocationDescription> entry : gameLayout.descriptions.entrySet()) {
                // loop through the descriptions and print the name of the location along wiht all of their attributes
                System.out.println(entry.getKey() + ": " + entry.getValue().getName() + ", " + entry.getValue().getTroopSpawnRate() + ", " + entry.getValue().getTroopCount() + ", " + entry.getValue().getOccupiedBy() + ", " + entry.getValue().getIsCapital() + ", " + entry.getValue().getColor());
            }
            return gameLayout;
        }
    }
    /**
     * Searches for the shortest path to the capital from the current location
     * @param currentLocation The current location
     */
    public void search(String currentLocation) {
        // create a queue to store the locations to be visited
        Queue<String> queue = new LinkedList<>();
        // create a set to store the visited locations
        Set<String> visited = new HashSet<>();
        // create a map to store the parent of each location
        Map<String, String> parent = new HashMap<>();
        // add the current location to the queue and the visited set
        queue.add(currentLocation);
        visited.add(currentLocation);
        // while the queue is not empty
        while (!queue.isEmpty()) {
            // remove the first location from the queue
            String location = queue.remove();
            // if the location is the capital
            if (descriptions.get(location).isCapital() && descriptions.get(location).getOccupiedBy() == 2) {
                // print the path to the capital
                printPath(parent, location);
                return;
            }
            // for each connected location
            Set<String> connectedLocations = connections.get(location);
            if (connectedLocations != null) {
                for (String connectedLocation : connectedLocations) {
                    // if the connected location has not been visited
                    if (!visited.contains(connectedLocation)) {
                        // add the connected location to the queue and the visited set
                        queue.add(connectedLocation);
                        visited.add(connectedLocation);
                        // set the parent of the connected location to the current location
                        parent.put(connectedLocation, location);
                    }
                }
            }
        }
        // if the capital is not reachable
        System.out.println("Capital is not reachable from the current location.");
    }

    /**
     * Prints the path to the capital
     * @param parent The parent of each location
     * @param location The current location
     */
    public void printPath(Map<String, String> parent, String location) {
        // create a list to store the path
        List<String> path = new ArrayList<>();
        // add the location to the path
        path.add(location);
        // while the location has a parent
        while (parent.containsKey(location)) {
            // add the parent to the path
            location = parent.get(location);
            path.add(location);
        }
        // print the path in reverse order
        System.out.println("Shortest path to the capital:");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.println(path.get(i));
        }
    }

    /**
     * Counts the total number of troops to spawn for the player
     * @return int The total number of troops to spawn
     */
    public int countAllNewTroops(){
        int totalTroops = 0;
        for (LocationDescription description : descriptions.values()) {
            totalTroops += description.getTroopSpawnRate();
        }
        return totalTroops;
    }

    /**
     * Updates the troop count for each location
     * @param location The location
     * @param troopCount The troop count
     */
    public void updateTroopCount(String location, int troopCount) {
        descriptions.get(location).setTroopCount(troopCount);
    }

    /**
     * Updates the occupied by for each location
     * @param player int for current player
     */
    public void returnControlledLocations(int player){
        for (String location : descriptions.keySet()) {
            if (descriptions.get(location).getOccupiedBy() == (player)) {
                System.out.println(location);
            }
        }
    }

    /**
     * Updates the player turn
     * @param pt int for current player
     */
    public void updatePlayerTurn(int pt){
        playerTurn = pt;
    }

    /**
     * Returns the player turn
     * @return int for current player
     */
    public int getPlayerTurn(){
        return playerTurn;
    }
    /**
     * Returns the game winner
     * @return int for current player
     */
    public int getGameWinner(){
        return gameWinner;
    }
    /**
     * Updates the game winner
     * @param winner int for current player
     */
    public void setGameWinner(int winner){
        gameWinner = winner;
    }
    /**
     * Increases turn by 1
     */
    public void nextTurn(){
        if(playerTurn == 4){
            playerTurn = 1;
        } else {
            playerTurn++;
        }
    }

    public boolean contains(String location) {
        return descriptions.containsKey(location);
    }

    public void moveTroopsToLocation(String fromLocation, String toLocation, int troopCount) {
        LocationDescription from = descriptions.get(fromLocation);
        LocationDescription to = descriptions.get(toLocation);
        if (from != null && to != null) {
            if(to.getOccupiedBy() == from.getOccupiedBy()){
                descriptions.get(fromLocation).removeTroopCount(troopCount);
                descriptions.get(toLocation).addTroopCount(troopCount);
            } else {
                attackLocation(fromLocation, toLocation, troopCount);
            }
        }
    }

    public void attackLocation(String fromLocation, String toLocation, int playerTroopCount) {
        LocationDescription from = descriptions.get(fromLocation);
        LocationDescription to = descriptions.get(toLocation);
        if (from != null && to != null) {
            // TODO: ADD RANDOMNESS TO THE BATTLE
            //get enemy troop count
            int enemyTroopCount = to.getTroopCount();
            if(playerTroopCount > enemyTroopCount){
                //player wins
                descriptions.get(fromLocation).removeTroopCount(playerTroopCount);
                // minus 2 to account for the 4 states of turns
                descriptions.get(toLocation).setOccupiedBy(playerTurn - 2);
                descriptions.get(toLocation).setTroopCount(playerTroopCount - enemyTroopCount);
                if(playerTurn == 3){
                    descriptions.get(toLocation).setColor("blue");
                } else {
                    descriptions.get(toLocation).setColor("red");
                }
                System.out.println("Player " + (playerTurn - 2) + " won the battle.");
            } else {
                //player loses
                descriptions.get(fromLocation).removeTroopCount(playerTroopCount);
                descriptions.get(toLocation).setTroopCount(enemyTroopCount - playerTroopCount);
                System.out.println("Player " + (playerTurn - 2) + " lost the battle.");
            }
        }
    }

    public void printAttackableLocations(int player) {
        ///  print locations that the player can attack, ie locations that are not occupied by the player, and have connections to the player's locations
        // print the location, the number of troops, then the locations it can be attacked from it, and the number of troops in those locations
        for (String location : descriptions.keySet()) {
            if (descriptions.get(location).getOccupiedBy() != player) {
                Set<String> connectedLocations = connections.get(location);
                for (String connectedLocation : connectedLocations) {
                    if (descriptions.get(connectedLocation).getOccupiedBy() == player) {
                        System.out.println(location + ": " + descriptions.get(location).getTroopCount() + " troops");
                        System.out.println("Can be attacked from: " + connectedLocation + " with " + descriptions.get(connectedLocation).getTroopCount() + " troops");
                    }
                }
            }
        }

    }

    public HashMap<String, LocationDescription> getDescriptions() {
        return descriptions;
    }

    public void printAllLocations() {
        for (String location : descriptions.keySet()) {
            System.out.println(location);
        }
    }

    public String getSelectedLocation() {
        for (String location : descriptions.keySet()) {
            if (descriptions.get(location).getSelected()) {
                return location;
            }
        }
        return null;
    }

    public void setSelectedLocation(String location) {
        // TODO: if new location is a valid location to move/attack, then dont set as selected, call attack method.
        if(descriptions.get(location).getSelected()){
            descriptions.get(location).deselect();
        } else {
            for (String loc : descriptions.keySet()) {
                descriptions.get(loc).deselect();
            }
            descriptions.get(location).select();
        }

    }

    public List<String> getConnections(String location) {
        List<String> connectedLocations = new ArrayList<>();
        Set<String> connected = connections.get(location);
        if (connected != null) {
            connectedLocations.addAll(connected);
        }
        return connectedLocations;
    }

    public void searchForCapital() {
    }

    public int getRealPlayerTurn() {
        if(playerTurn%2 == 0){
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * retrun if the move is valid from the current location
     * @param name the name of the location to move to
     * @return boolean if the move is valid
     */
    public boolean isValidMove(String name) {
        if(GameDriver.getPlayer1Location() == null){
            GameDriver.setPlayer1Location(getCapital(1));
        }
        if(GameDriver.getPlayer2Location() == null){
            GameDriver.setPlayer2Location(getCapital(2));
        }
        if(getSelectedLocation() == null) {
            if (getRealPlayerTurn() == 1) {
                setSelectedLocation(GameDriver.getPlayer1Location());
            } else {
                setSelectedLocation(GameDriver.getPlayer2Location());
            }
        }
        Iterator<String> iterator = getConnectionsIterator(getSelectedLocation());
        while (iterator.hasNext()) {
            String location = iterator.next();
            if (location.equals(name)) {
                if(descriptions.get(name).getOccupiedBy() == getRealPlayerTurn()){
                    return true;
                }
                return true;
            }
        }
        return false;
    }
}