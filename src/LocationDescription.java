public class LocationDescription {
    private String name;
    private int troopSpawnRate;
    private int troopCount;
    private int occupiedBy;
    private boolean isCapital;
    private String color;

    public LocationDescription(String name, int troopSpawnRate, int troopCount, int occupiedBy, boolean isCapital, String color) {
        this.name = name;
        this.troopSpawnRate = troopSpawnRate;
        this.troopCount = troopCount;
        this.occupiedBy = occupiedBy;
        this.isCapital = isCapital;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getTroopSpawnRate() {
        return troopSpawnRate;
    }

    public int getTroopCount() {
        return troopCount;
    }

    public int getOccupiedBy() {
        return occupiedBy;
    }

    public boolean getIsCapital() {
        return isCapital;
    }

    public String getColor() {
        return color;
    }
    public void setTroopCount(int troopCount) {
        this.troopCount = troopCount;
    }
    public void setOccupiedBy(int occupiedBy) {
        this.occupiedBy = occupiedBy;
    }
    public void setIsCapital(boolean isCapital) {
        this.isCapital = isCapital;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setTroopSpawnRate(int troopSpawnRate) {
        this.troopSpawnRate = troopSpawnRate;
    }
    public void addTroopCount(int troopCount) {
        this.troopCount += troopCount;
    }
    public void removeTroopCount(int troopCount) {
        this.troopCount -= troopCount;
    }
}



/*
Territory
troop spawn rate
troop count
ocuppied by (0 - neutral, 1 - player 1, 2 - player 2)
is capital (Randomly generated for a end row node, adds 3 troops per turn)
COLOR (in caps so it can be easily read by the program)
 */