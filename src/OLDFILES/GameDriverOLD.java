//package OLDFILES;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.Scanner;
//
//// TODO FiX File save and load
//// TODO Add GUI
//// TODO MAYBE GET RID OF RANDOM CAPITALS
//public class GameDriverOLD {
//   private GameLayout gameLayout;
//   private String currentLocation;
//
//   public GameDriverOLD(GameLayout gameLayout, String startingLocation) {
//       this.gameLayout = gameLayout;
//       this.currentLocation = startingLocation;
//   }
//
//   public void listAllLocations() {
//       Iterator<String> iterator = gameLayout.getLocationIterator();
//       while (iterator.hasNext()) {
//           System.out.println(iterator.next());
//       }
//   }
//
//   public void listCurrentLocationProperties() {
//       LocationDescription description = gameLayout.getLocationDescription(currentLocation);
//       System.out.println("Location: " + description.getName());
//       System.out.println("Troop Spawn Rate: " + description.getTroopSpawnRate());
//       System.out.println("Troop Count: " + description.getTroopCount());
//       System.out.println("Occupied By: " + description.getOccupiedBy());
//       System.out.println("Is Capital: " + description.getIsCapital());
//       System.out.println("Color: " + description.getColor());
//   }
//
//  public void listConnectedLocations() {
//   System.out.println("Current Location: " + currentLocation);
//   Iterator<String> iterator = gameLayout.getConnectionsIterator(currentLocation);
//   if (!iterator.hasNext()) {
//       System.out.println("No connections found.");
//       return;
//   }
//   while (iterator.hasNext()) {
//       System.out.println(iterator.next());
//   }
//}
//
//   public void moveToLocation(String newLocation) {
//   Iterator<String> iterator = gameLayout.getConnectionsIterator(currentLocation);
//   boolean isConnected = false;
//   while (iterator.hasNext()) {
//       if (iterator.next().equals(newLocation)) {
//           isConnected = true;
//           break;
//       }
//   }
//   if (isConnected) {
//       //currentLocation.setNotCurrentNode();
//       currentLocation = newLocation;
//       //currentLocation.setCurrentNode();
//       System.out.println("Moved to: " + currentLocation);
//   } else {
//       System.out.println("Invalid move. Location not connected.");
//   }
//}
//
////    public void saveGameState(String connectionsFile, String descriptionsFile) throws FileNotFoundException {
////        gameLayout.saveToFile(connectionsFile, descriptionsFile);
////        System.out.println("Game state saved.");
////    }
//   public void saveGameState(String filename) throws IOException {
//   gameLayout.saveGameLayout(filename);
//   System.out.println("Game state saved.");
//}
//
//   public static GameDriverOLD loadGameState(String filename, String startingLocation) throws IOException, ClassNotFoundException {
//       GameLayout gameLayout = GameLayout.loadGameLayout(filename);
//       return new GameDriverOLD(gameLayout, startingLocation);
//   }
//   public GameDriverOLD loadGameInteractions(Scanner scanner, GameDriverOLD gameDriver) throws IOException, ClassNotFoundException {
//       while (true) {
//           System.out.println("Choose an action: \n1. Start New Game\n2. Load Saved Game");
//           String answer = scanner.nextLine();
//
//           if (answer.equals("2")) {
//               gameDriver = loadGameState("gameState.ser", "Territory A1");
//               //gameLayout.loadFromFile("src/connectionsSaved.txt", "src/descriptionsSaved.txt");
//               /* Not planning on having a true starting location unless needed */
//               //gameDriver = new GameDriver(gameLayout, "Territory A1");
//               break;
//           } else if (answer.equals("1")) {
//               int randomNum = (int) (Math.random() * 3) + 1;
//               int randomNum2 = (int) (Math.random() * 3) + 1;
//               gameLayout.loadFromFile("src/nodeConnections.txt", "src/locations.txt");
//               gameLayout.setCapital("Territory A" + randomNum);
//               gameLayout.setCapital("Territory E" + randomNum2);
//               gameDriver = new GameDriverOLD(gameLayout, "Territory A" + randomNum);
//               System.out.println("Starting location: Territory A" + randomNum);
//               break;
//           } else {
//               System.out.println("Invalid input. Please try again.");
//           }
//       }
//       return gameDriver;
//   }
//
//   public static void main(String[] args) throws IOException, ClassNotFoundException {
//       Scanner scanner = new Scanner(System.in);
//       GameLayout gameLayout = new GameLayout();
//       GameDriverOLD gameDriver = new GameDriverOLD(gameLayout, "Territory A1");
//
//       while (true) {
//           System.out.println("Choose an action: \n1. Start New Game\n2. Load Saved Game");
//           String answer = scanner.nextLine();
//
//           if (answer.equals("2")) {
//               gameDriver = loadGameState("gameState.ser", "Territory A1");
//               //gameLayout.loadFromFile("src/connectionsSaved.txt", "src/descriptionsSaved.txt");
//               /* Not planning on having a true starting location unless needed */
//               //gameDriver = new GameDriver(gameLayout, "Territory A1");
//               break;
//           } else if (answer.equals("1")) {
//               int randomNum = (int) (Math.random() * 3) + 1;
//               int randomNum2 = (int) (Math.random() * 3) + 1;
//               gameLayout.loadFromFile("src/nodeConnections.txt", "src/locations.txt");
//               gameLayout.setCapital("Territory A" + randomNum);
//               gameLayout.setCapital("Territory E" + randomNum2);
//               gameDriver = new GameDriverOLD(gameLayout, "Territory A" + randomNum);
//               System.out.println("Starting location: Territory A" + randomNum);
//               break;
//           } else {
//               System.out.println("Invalid input. Please try again.");
//           }
//       }
//       while (true) {
//           System.out.println("Choose an action: \n1. List All Locations\n2. List Current Location Properties\n3. list Connected Locations\n4. Move To Location\n5. Save Game State\n6. Search for Capital\n7. Exit Game");
//           String action = scanner.nextLine();
//           switch (action) {
//               case "1":
//                   gameDriver.listAllLocations();
//                   break;
//               case "2":
//                   gameDriver.listCurrentLocationProperties();
//                   break;
//               case "3":
//                   gameDriver.listConnectedLocations();
//                   break;
//               case "4":
//                   System.out.println("Enter new location:");
//                   String newLocation = scanner.nextLine();
//                   gameDriver.moveToLocation(newLocation);
//                   break;
//               case "5":
//                   try {
//                       gameDriver.saveGameState("gameState.ser");
//                   } catch (IOException e) {
//                       System.err.println("Error saving game state: " + e.getMessage());
//                   }
//                   //gameDriver.saveGameState("src/connectionsSaved.txt", "src/descriptionsSaved.txt");
//                   break;
//               case "6":
//                   //search for the capital and save it
//                   gameLayout.search(gameDriver.currentLocation);
//                   break;
//               case "7":
//                   System.out.println("Exiting game.");
//                   scanner.close();
//                   return;
//               default:
//                   System.out.println("Invalid action.");
//           }
//       }
//
//   }
//}
