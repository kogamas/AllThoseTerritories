package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {

    private String name = "";
    private List<Continent> continents = new ArrayList<>();
    private boolean areAllIdsSet = false;   //ids can only be set once

    public World() {
    }

    /**
     * This method sets a name for World object
     * @param name should be the filename of the .map without .map
     */
    public void setName(String name) {
        if (this.name.equals("")) {
            this.name = name;
        }
        else System.err.println("Error: World already has a name!");
    }

/**
 * This method sets Ids for all territories if ids have not yet been set
 * hunderterstelle identifies the continent
 * letzten 2 stellen identifies the territory
 */
    public void setAllIds() {   //territory max for 1 continent is 100
        if (!this.areAllIdsSet) {
            for (int i = 0; i < continents.size(); i++) {
                continents.get(i).setId(i*1000);
                for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                    continents.get(i).getTerritories().get(j).setId((i*100)+(j));
                }
            }
        this.areAllIdsSet = true;
        }
    }

    public void addContinent(Continent c) {
            this.continents.add(c);
        }

    /**
     * @return returns a Map with the TerritoryId as Key and an Array of Patches as Value
     **/
    public Map<Integer,Patch[]> getTerritoryMap() {
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
     *
     * @return int[n][2] n = number of territories, nested Array[0] = territoryId, nested Array[1] = controlledByPlayer
     */
    public int[][] getTerritoryBelonging() {
        int[][] array = new int[this.countTerritories()][2];
        int count = 0;

        for (int i = 0; i < continents.size(); i++) {
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                array[count][0] = continents.get(i).getTerritories().get(j).getId();
                array[count][1] = continents.get(i).getTerritories().get(j).getControlledByPlayer();

                count++;
            }
        }
        return array;
    }

    /**
     *
     * @return  int This returns the number of territories
     */
    public int countTerritories() {
        int countTerritories = 0;
        for (int i = 0; i < continents.size(); i++) {
            for (int j = 0; j < continents.get(i).getTerritories().size(); j++) {
                countTerritories++;
            }
        }
        return countTerritories;
    }
}

