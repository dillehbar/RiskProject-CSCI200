import java.io.*;
import java.util.*;

public class GameLayout {
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

            // Add the description to the map
            descriptions.put(name, new LocationDescription(name, troopSpawnRate, troopCount, occupiedBy, isCapital, color));

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
        return Set.<String>of().iterator();
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
        try (PrintWriter writer = new PrintWriter(new File(connectionsFile))) {
            for (Map.Entry<String, Set<String>> entry : connections.entrySet()) {
                writer.println(entry.getKey());
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
}