package Board;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Territory{
    private String name;
    private Point capital;
    private int id;
    private List<Patch> patches = new LinkedList<>();
    private List<Territory> neighbors = new LinkedList<>();
    private int army = 0;
    private int controlledByPlayer = -1;

    Territory(String name) {
        this.name = name;
    }

    /**
     * This method adds a Capital to the Territory
     * @param capital  This is the capital as a Point (java.awt.Point)
     */
   void addCapital(Point capital) {
        this.capital = capital;
    }
    /**
     * This method adds a Patch of Land to the Territory
     * @param p  This is a Patch of Land
     */
   void addPatch(Patch p) {
        this.patches.add(p);
    }
    /**
     * This method adds a Neighbor to this Territory
     * @param t  This is a Territory added as a Neighbor
     */
   void addNeighbor(Territory t) {
        this.neighbors.add(t);
    }

    /**
     * This method adds Armies to the Territory
     * @param count This is the number of armies added to the territory
     */
    void addArmy(int count) {
        this.army += count;
    }

    /**
     * This method subtracts Armies from the Territory
     * @param count This is the number of armies subtracted from the territory
     */
    void subArmy(int count) {
        this.army -= count;
    }

    /**
     * This method returns size of the Army
     * @return This returns the size of the Army
     */
    int getArmy() {
        return army;
    }

    /**
     *
     * @return an array of Patches
     */
    Patch[] getPatches() {
        return patches.toArray(new Patch[patches.size()]);
    }

    /**
     *
     * @return a List full of Point arrays(the Patches)
     */
    List<Point[]> getPointArrayList() {
        List<Point[]> tempList = new LinkedList<>();

        for (Patch p: patches) {
            tempList.add(p.getBorders());
        }
        return tempList;
    }
    /**
     * This method is used to check who is controlling this Territory
     * @return int This returns a int representation of the Player controlling this Territory
     */
    int getControllingPlayerId() {
        return controlledByPlayer;
    }

    /**
     *
     * @return int This returns the Id of this Territory
     */
    int getId() {
        return id;
    }

    /**
     * This method sets the Id of this Territory
     * @param id This is the id which is set
     */
    void setId(int id){
        this.id = id;
    }

    /**
     * This method changes the controlling Player
     * @param player This is the new Player controlling this Territory
     */
    void setControlledByPlayer(int player) {
        this.controlledByPlayer = player;
    }

    String getName() {
        return name;
    }

    /**
     * This method checks if this territory is a neighbor of that territory
     * @param neighborId neighbor to check
     * @return boolean is this neighbor of that
     */
    boolean isNeighborOf(int neighborId) {
        boolean check = false;
        for (Territory n:neighbors) {
            if(n.getId() == neighborId) {
                check = true;
            }
            System.out.println("Is "+this.getId()+" neighbor of "+ n.getId() + " "+ check);
        }
        return check;
    }
        //just for testing
    /*int neighborCount() {
       return neighbors.size();
    }
    String neighborName() {
        String ret = "*";
        for (Territory t:neighbors) {
           ret += t.getName() + " ";
        }
        return this.name + ": "+ ret + "*";
    }*/
}
