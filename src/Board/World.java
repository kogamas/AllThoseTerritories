package Board;

import java.awt.Point;
import java.util.*;

class World implements Board{

    private String name = "";
    private List<Continent> continents = new LinkedList<>();
    private boolean areAllIdsSet = false;   //ids can only be set once

    World() {
    }

    /**
     * This method sets a name for World object
     * @param name should be the filename of the .map without .map
     */
    void setName(String name) {
        if (this.name.equals("")) {
            this.name = name;
        }
        else System.err.println("Error 09: World already has a name!");
    }

/**
 * This method sets Ids for all territories if ids have not yet been set
 * hunderterstelle identifies the continent
 * letzten 2 stellen identifies the territory
 */
    void setAllIds() {   //territory max for 1 continent is 100
        if (!this.areAllIdsSet) {
            for (int i = 1; i < continents.size()+1; i++) {
                continents.get(i-1).setId(i*1000);
                for (int j = 1; j < continents.get(i-1).getTerritories().size()+1; j++) {
                    continents.get(i-1).getTerritories().get(j-1).setId((i*1000)+(j));
                }
            }
        this.areAllIdsSet = true;
        }
    }

    void addContinent(Continent c) {
            this.continents.add(c);
        }

    boolean hasNoContinents() {return this.continents.isEmpty();}

    /**
     * @return returns a Map with the TerritoryId as Key and an Array of Patches as Value
     **/
    Map<Integer,Patch[]> getTerritoryPatchesMap() {
        Map<Integer,Patch[]> territoryMap = new HashMap<>();

        for (int i = 0; i < continents.size(); i++) {
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                int tempId = continents.get(i).getTerritories().get(j).getId();
                Patch[] tempArr = continents.get(i).getTerritories().get(j).getPatches();
                territoryMap.put(tempId, tempArr);
            }
        }
        return territoryMap;
    }
    /**
     * @return returns a Map with the TerritoryId as Key and an Array of Patches as Value
     **/
    @Override
    public Map<Integer,List<Point[]>> getTerritoryPointArrayMap() {
        Map<Integer, List<Point[]>> pointMap = new HashMap<>();
        List<Point[]> tempList = new LinkedList<>();

        for (Continent c : continents) {
            for (Territory t : c.getTerritories()) {
                pointMap.put(t.getId(), t.getPointArrayList());
            }
        }
        return pointMap;
    }

    /**
     * @return returns a Map with the TerritoryId as Key and the Territory as a Value
     **/
    @Override
    public Map<Integer,Territory> getTerritoryMap() {
        Map<Integer,Territory> territoryMap = new HashMap<>();

        for (int i = 0; i < continents.size(); i++) {
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                int tempId = continents.get(i).getTerritories().get(j).getId();
                Territory tempTerritory = continents.get(i).getTerritories().get(j);
                territoryMap.put(tempId, tempTerritory);
            }
        }
        return territoryMap;
    }

    /**
     *
     * @return int[n][2] n = number of territories, nested Array[0] = territoryId, nested Array[1] = controlledByPlayer
     */
    int[][] getTerritoryBelonging() {
        int[][] array = new int[this.countTerritories()][2];
        int count = 0;

        for (int i = 0; i < continents.size(); i++) {
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                array[count][0] = continents.get(i).getTerritories().get(j).getId();
                array[count][1] = continents.get(i).getTerritories().get(j).getControllingPlayerId();

                count++;
            }
        }
        return array;
    }

    /**
     *
     * @return  int This returns the number of territories
     */
    @Override
    public int countTerritories() {
        int countTerritories = 0;
        for (int i = 0; i < continents.size(); i++) {
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                countTerritories++;
            }
        }
        return countTerritories;
    }

    @Override
    public int getTerritoryId(Point p) {
        return 0;   //todo: implement this with sub method is in territory, then iterate through all territories
    }

    @Override
    public int getControllingPlayerId(int territoryId) {
        int continentId = territoryId / 1000;
        continentId *= 1000;
        for (Continent c : continents) {
            if (c.getId() == continentId) {
                for (Territory t : c.getTerritories()) {
                    if (t.getId() == territoryId) {
                        return t.getControllingPlayerId();
                    }
                }

            }
        }
        return -1;
    }

    /**
     * This method checks if all territories are controlled by the same player
     * @param
     * @return
     */
    @Override
    public boolean isGameOver() {
        boolean controllingPlayerSet = false;
        int controllingPlayer = -1;

        for (Continent c : continents) {
                for (Territory t : c.getTerritories()) {
                    if (!controllingPlayerSet) {
                        controllingPlayer = t.getControllingPlayerId();
                        controllingPlayerSet = true;
                    }

                    if (t.getControllingPlayerId() != controllingPlayer ) {
                        return false;
                    }
                }

            }
        return true;
    }


    private Territory getTerritory(int territoryId) {
        int continentId = territoryId / 1000;
        continentId *= 1000;
        for (Continent c : continents) {
            if (c.getId() == continentId) {
                for (Territory t : c.getTerritories()) {
                    if (t.getId() == territoryId) {
                        return t;
                    }
                }

            }
        }
        return null;
    }

    @Override
    public int getArmy(int territoryId) {
        if (validTerritoryId(territoryId)) {
            return getTerritory(territoryId).getArmy();
        }
        else return -1;
    }

    /**
     * Just for testing fixme!!!
     * @return
     */
    public List<Continent> getContinents() {
        return continents;
    }

    public void addArmy(int territoryId, int n) {
        if (validTerritoryId(territoryId)) {
            getTerritory(territoryId).addArmy(n);
        }
        else System.err.println("Error 13: False territory Id");
    }

    public void printTerritories() {
        for (Continent c: continents) {
            for (Territory t: c.getTerritories()) {
                System.out.println("Id: " + t.getId() + " Army: " + t.getArmy() + " Player: " + t.getControllingPlayerId() + " Name: " + t.getName().substring(1));
            }
        }
    }

    @Override
    public boolean validTerritoryId(int territoryId) {
        int continentId = territoryId / 1000;
        continentId *= 1000;
        for (Continent c : continents) {
            if (c.getId() == continentId) {
                for (Territory t : c.getTerritories()) {
                    if (t.getId() == territoryId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void setControllingPlayer(int territoryId, int playerId) {
        int continentId = territoryId / 1000;
        continentId *= 1000;
        for (Continent c : continents) {
            if (c.getId() == continentId) {
                for (Territory t : c.getTerritories()) {
                    if (t.getId() == territoryId) {
                        t.setControlledByPlayer(playerId);
                    }
                }
            }
        }
    }

    @Override
    public String getName(int id) {
        for (Continent c : continents) {
            if (c.getId() == id) {
                return c.getName();
            }
            else {
                for (Territory t : c.getTerritories()) {
                    if (t.getId() == id) {
                        return t.getName();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Point getCapital(int territoryId) {
        for (Continent c : continents) {
            if (c.getId() == territoryId) {
                return null;
            }
            else {
                for (Territory t : c.getTerritories()) {
                    if (t.getId() == territoryId) {
                        return t.getCapital();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int calculateReinforcement(int playerId) {
        int count = 0;
        int bonus = 0;
        for (Continent c : continents) {
            boolean allTerritories = true;
            for (Territory t : c.getTerritories()) {
                if (t.getControllingPlayerId() == playerId) {
                    count++;
                }
                else allTerritories = false;
            }
            if (allTerritories) {
               bonus += c.getBonus();
            }
        }

        return (count / 3) + bonus;
    }

    public boolean isNeighborOf(int territoryId, int neighborId) {
        if (validTerritoryId(territoryId) && validTerritoryId(neighborId)) {
            return getTerritory(territoryId).isNeighborOf(neighborId);
        }
        else return false;
    }

    public Map<Integer, int[]> getNeighborsList() {
        Map<Integer, int[]> neighborMap = new HashMap<>();
        for (Continent c:continents) {
            for (Territory t: c.getTerritories()) {
                int[] temp = t.getNeighborsIds();
                neighborMap.put(t.getId(), temp);
            }
        }
        return neighborMap;
    }

    @Override
    public boolean isAllClaimed() {

        for (Continent c: continents) {
            for (Territory t: c.getTerritories()) {
              if (t.getControllingPlayerId() < 0) {     //playerId default is -1
                return false;
              }
            }
        }

        return true;
    }

    public int getRandomTerritoryId(int playerId) {

        int count = 0;
        for (Continent c: continents) {
            for (Territory t: c.getTerritories()) {
                if (t.getControllingPlayerId() == playerId) {     //playerId default is -1
                    System.out.println("controlled by:" + t.getControllingPlayerId());
                    count++;
                }
            }
        }
        System.out.println("The count is:" + count);
        Random rand = new Random();
        int value = rand.nextInt(count);
        System.out.println("The random is:" + value);

        count = 0;
        for (Continent c: continents) {
            for (Territory t: c.getTerritories()) {
                if (t.getControllingPlayerId() == playerId) {     //playerId default is -1
                    if (value == count) {
                        System.out.println("t.getId()" + t.getId());
                        return t.getId();
                    }

                    count++;
                }
            }
        }
        System.err.println("Error 24: Error in method, something went terribly wrong!");
        return -1;

    }
}

