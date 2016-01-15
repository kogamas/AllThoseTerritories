package game;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Territory {
    private String name;
    private Point capital;
    private int id;
    private List<Patch> patches = new LinkedList<>();
    private List<Territory> neighbors = new LinkedList<>();
    private int army = 0;
    private int controlledByPlayer = -1;

    public Territory(String name) {
        this.name = name;
    }

    public Territory(String name, Point capital) {
        this.name = name;
        this.capital = capital;
    }

    /**
     * This method adds a Capital to the Territory
     * @param capital  This is the capital as a Point (java.awt.Point)
     */
   public void addCapital(Point capital) {
        this.capital = capital;
    }
    /**
     * This method adds a Patch of Land to the Territory
     * @param p  This is a Patch of Land
     */
   public void addPatch(Patch p) {
        this.patches.add(p);
    }
    /**
     * This method adds a Neighbor to this Territory
     * @param t  This is a Territory added as a Neighbor
     */
   public void addNeighbor(Territory t) {
        this.neighbors.add(t);
    }
    /**
     * This method adds multible Neighbors to this Territory
     * @param terArray  This is an array of Territories added as a Neighbors
     */
   public void addNeighbor(Territory[] terArray) {
        for (Territory t : terArray) {
            List<Territory> newList = Arrays.asList(terArray);
            this.neighbors.addAll(newList);
        }
    }

    /**
     * This method adds Armies to the Territory
     * @param count This is the number of armies added to the territory
     */
    public void addArmy(int count) {
        this.army += count;
    }

    /**
     * This method subtracts Armies from the Territory
     * @param count This is the number of armies subtracted from the territory
     */
    public void subArmy(int count) {
        this.army -= count;
    }

    /**
     * This method returns size of the Army
     * @return This returns the size of the Army
     */
    public int getArmy() {
        return army;
    }

    /**
     *
     * @return an array of Patches
     */
    public Patch[] getPatches() {
        return patches.toArray(new Patch[patches.size()]);
    }

    /**
     * This method is used to check who is controlling this Territory
     * @return int This returns a int representation of the Player controlling this Territory
     */
    public int getControlledByPlayer() {
        return controlledByPlayer;
    }

    /**
     *
     * @return int This returns the Id of this Territory
     */
    public int getId() {
        return id;
    }

    /**
     * This method sets the Id of this Territory
     * @param id This is the id which is set
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * This method changes the controlling Player
     * @param player This is the new Player controlling this Territory
     */
    void setControlledByPlayer(int player) {
        this.controlledByPlayer = player;
    }
}
