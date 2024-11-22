import java.io.*;
import java.util.*;

public class GameLayout implements Serializable {
    private HashMap<String, Set<String>> connections;
    private HashMap<String, LocationDescription> descriptions;

    public GameLayout() {
        connections = new HashMap<>();
        descriptions = new HashMap<>();
    }

    public void loadFromFile(String connectionsFile, String descriptionsFile) throws FileNotFoundException {
        loadDescriptions(descriptionsFile);
        loadConnections(connectionsFile);
    }

    public void setCapital(String location) {
        for (LocationDescription description : descriptions.values()) {
            description.setIsCapital(false);
        }
        descriptions.get(location).setIsCapital(true);
    }

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

    public Iterator<String> getLocationIterator() {
        return descriptions.keySet().iterator();
    }

    public Iterator<String> getConnectionsIterator(String location) {
    if (connections.containsKey(location)) {
        return connections.get(location).iterator();
    }
    return null;
}
    public LocationDescription getLocationDescription(String location) {
        return descriptions.get(location);
    }

    public void updateLocationDescription(String location, LocationDescription newDescription) {
        descriptions.put(location, newDescription);
    }

    public void saveToFile(String connectionsFile, String descriptionsFile) throws FileNotFoundException {
        saveConnections(connectionsFile);
        saveDescriptions(descriptionsFile);
    }

    private void saveConnections(String connectionsFile) throws FileNotFoundException {
        // first print the location, new line then the number of connections, new line then the connections
        try (PrintWriter writer = new PrintWriter(new File(connectionsFile))) {
            for (Map.Entry<String, Set<String>> entry : connections.entrySet()) {
                writer.println(entry.getKey());
                writer.println(entry.getValue().size());
                for (String connectedLocation : entry.getValue()) {
                    writer.println(connectedLocation);
                }
                writer.println();
            }
        }
    }

    private void saveDescriptions(String descriptionsFile) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(descriptionsFile))) {
            for (LocationDescription description : descriptions.values()) {
                writer.println(description.getName());
                writer.println(description.getTroopSpawnRate());
                writer.println(description.getTroopCount());
                writer.println(description.getOccupiedBy());
                writer.println(description.getIsCapital());
                writer.println(description.getColor());
                writer.println();
            }
        }
    }
    public void saveGameLayout(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public static GameLayout loadGameLayout(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (GameLayout) ois.readObject();
        }
    }
    // create a search method that will breadth first search the graph to find the shortest path between the current location and the p2 capital
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

}