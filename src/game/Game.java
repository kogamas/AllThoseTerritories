package game;

import java.awt.*;
import java.util.*;

public interface Game {

    //Phase 0

    /**
     * This method returns an array of all player names
     * @return String[] This returns all players
     */
    String [] getPlayerNames(); //first array entry is first player, second is second

    /**
     * This method returns a Map with all the neighborsIds of a territoryId
     * @return a Map with all the neighborsIds of a territoryId
     */
    public Map<Integer, int[]> getNeighborsList();

    /**
     * This method claims a territory for a player
     * @param playerId  The player who claims
     * @param territoryId   The territory which is claimed
     */
    void claimTerritory(int playerId, int territoryId);

    void nextPhase();       //can only be executed once

    //Phase 1

  //boolean isGameOver();


    void nextRound();   //could check if GameOver

    /**
     * This method adds reinforcements to a player
     * @param playerId Player the reinforcements are added
     */
    void calculateReinforcement(int playerId);  //adds the reinforcements to a player

    /**
     * This method reinforces a Territory with armies from the reinforcements
     * @param playerId  Player who is reinforcing
     * @param territoryId   Territory which is reinforced
     * @param numberOfArmies    How many armies are reinforced
     */
    void moveReinforcement(int playerId, int territoryId, int numberOfArmies);     //sets an army on a territory

    /**
     * This method moves armies from a territory to another if they are controlled by the same player
     * @param fromTerritoryId   Territory where the armies move from
     * @param toTerritoryId     Territory where the armies move to
     * @param numberOfArmies    how many armies move (should only be 1)
     */
    void moveArmy(int fromTerritoryId, int toTerritoryId, int numberOfArmies);    //could return boolean if move is allowed

    /**
     * This method attacks a territory from another if they are controlled by two different players
     * @param fromTerritoryId   attacking territory
     * @param toTerritoryId     defending territory
     */
    void attack(int fromTerritoryId, int toTerritoryId);  //return must tell won, lost and how many casualties


    /**
     * info the ui needs
     */

    /**
     * This method returns a Map with territoryIds and a List of Point arrays (the borders of the territory)
     * @return a Map with territoryIds and a List of Point arrays (the borders of the territory)
     */
    Map<Integer, java.util.List<Point[]>> getTerritoryPointArrayMap();
    /**
     * @return This returns the Name of a continent or territory
     */
    String getName(int id);

    /**
     * This method returns the controlling player of a territory
     * @param territoryId
     * @return int This returns the playerId of the player controlling the territory(param)
     */
    int getControllingPlayerId(int territoryId);

    /**
     * This method returns the army count for a territory
     * @param territoryId
     * @return int This returns how many armies are stationed in a territory
     */
    int getArmy(int territoryId);

    /**
     * This method returns the name to a playerId
     * @param playerId
     * @return String This returns the name of playerId
     */
    String getPlayerName(int playerId);

    //int[] getPlayerColor(int playerId);


    /*
     * just for Testing
     */
    void printTerritories();

}
