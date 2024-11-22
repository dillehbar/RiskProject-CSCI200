public class LocationDescription implements java.io.Serializable {
    private String name;
    private int troopSpawnRate;
    private int troopCount;
    private int occupiedBy;
    private static boolean isCapital;
    private String color;
    private boolean mark;
    //private boolean isCurrentNode;
    //private static final long serialVersionUID = 1L;
    public LocationDescription(String name, int troopSpawnRate, int troopCount, int occupiedBy, boolean isCapital, String color/*, Boolean isCurrentNode*/) {
        this.name = name;
        this.troopSpawnRate = troopSpawnRate;
        this.troopCount = troopCount;
        this.occupiedBy = occupiedBy;
        this.isCapital = isCapital;
        this.color = color;
        this.mark = false;
        //this.isCurrentNode = isCurrentNode;
    }

    public String getName() {
        return name;
    }
//    public boolean getIsCurrentNode(){
//      return isCurrentNode;
//    }
//    public void setCurrentNode(){
//      this.isCurrentNode = true;
//    }
//    public void setNotCurrentNode(){
//      this.isCurrentNode = false;
//    }
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
    public boolean isCapital() {
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
    public void mark() {
        this.mark = true;
    }
    public void unmark() {
        this.mark = false;
    }
    public void setMark(boolean mark) {
        this.mark = mark;
    }
    public boolean getMark() {
        return mark;
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