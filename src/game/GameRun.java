package game;

import Board.Board;
import Board.Reader;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameRun implements Game{
    private Board board;
    private int phaseOfGame = -1;
    private int roundOfPhase = -1;
    private Map<Integer, Player> playerMap = new HashMap<>();

    public GameRun(String player1, String player2, String[] args){
        this.playerMap.put(1, new Player(player1));
        this.playerMap.put(2, new Player(player2));
        startGame(args);
    }


    private boolean validPlayerId(int playerId) {
        return playerMap.containsKey(playerId);
    }

    private boolean validTerritoryId(int territoryId) {
        return board.validTerritoryId(territoryId);
    }

    private Player getPlayer(int playerId) {
        if (validPlayerId(playerId)) {
            return playerMap.get(playerId);
        }
        else {
            System.err.println("Invalid PlayerId");
            return null;
        }
    }
    
    private boolean isNeighbor(int territoryId, int neighborId) {
       return board.isNeighborOf(territoryId, neighborId);
    }

    public void startGame(String[] array) {
        this.board = new Reader(array).getWorld();
        this.phaseOfGame = 0;
        this.roundOfPhase = 0;
    }

    @Override
    public void nextPhase() {
        this.phaseOfGame++;
        this.roundOfPhase = 0;
    }

    @Override
    public void nextRound() {
        this.roundOfPhase++;
    }

    @Override
    public void calculateReinforcement(int playerId){
        if (getPlayer(playerId) != null) {
            getPlayer(playerId).setReinforcement(board.calculateReinforcement(playerId));
        }
        else System.err.println("Error 20: Invalid player Id");
        System.out.println("Reinforcement player" + playerId + ": " + board.calculateReinforcement(playerId));
    }

    /**
     * This method claims a Territory
     * @param playerId  The player who claims
     * @param territoryId   The territory which is claimed
     * @return  Boolean false when all all territories are claimed
     */
    @Override
    public void claimTerritory(int playerId, int territoryId) {

        int territoryControlledByPlayer = board.getControllingPlayerId(territoryId);

        if (validPlayerId(playerId) && validTerritoryId(territoryId)) {
            if (territoryControlledByPlayer < 0) {
                board.addArmy(territoryId, 1);
                board.setControllingPlayer(territoryId, playerId);
            }
        }
        else System.err.println("Error 01: invalid PlayerId or TerritoryId entered");
    }

    @Override
    public int showReinforcement(int playerId) {
        if (getPlayer(playerId) != null) {
            return getPlayer(playerId).getReinforcement();
        }
        else {
            System.err.println("Error 21: Invalid player Id");
            return -1;
        }
    }

    /**
     * This method reinforces a territory
     * @param playerId  Player who is reinforcing
     * @param territoryId   Territory which is reinforced
     * @param numberOfArmies    How many armies are reinforced
     * @return boolean false when reinforcements of the player are depleted
     */
    @Override
    public boolean moveReinforcement(int playerId, int territoryId, int numberOfArmies) {

        if (getPlayer(playerId) != null) {
            int territoryControlledByPlayer = board.getControllingPlayerId(territoryId);
            int reinforcement = getPlayer(playerId).getReinforcement();
            System.out.println("Reinforcement " + playerId + ": " + reinforcement);
            if (validPlayerId(playerId) && validTerritoryId(territoryId) && reinforcement > 0) {
                if (playerId == territoryControlledByPlayer && reinforcement >= numberOfArmies) {
                    board.addArmy(territoryId, numberOfArmies);
                    getPlayer(playerId).subReinforcement(numberOfArmies);
                } else
                    System.err.println("Error 02: Territory does not belong to you or no more reinforcements available");
            } else System.err.println("Error 03: invalid PlayerId or TerritoryId entered");

            if (getPlayer(playerId).hasReinforcement()) {
                return true;
            }
            else return false;
        }
        else  System.err.println("Error 22: Invalid player Id");

        return true; //todo: implement reinforcement move end logic
    }

    @Override
    public void moveArmy(int fromTerritoryId, int toTerritoryId, int numberOfArmies) {
        if (isNeighbor(fromTerritoryId,toTerritoryId)) {    //check if the 2 territories are adjacent

            int fromTerritoryControlledByPlayer = board.getControllingPlayerId(fromTerritoryId);
            int toTerritoryControlledByPlayer = board.getControllingPlayerId(toTerritoryId);

            if (validTerritoryId(toTerritoryId) && validTerritoryId(fromTerritoryId)) {     //check if both territory ids are correct
                if (fromTerritoryControlledByPlayer == toTerritoryControlledByPlayer) {     //check if both territories belong to the same player
                    if (board.getArmy(fromTerritoryId) >= numberOfArmies + 1) {                        //check if enough armies are in territory
                        board.addArmy(fromTerritoryId, -numberOfArmies);    //subtract by adding negative
                        board.addArmy(toTerritoryId, numberOfArmies);
                    }
                    else System.err.println("Error 11: You dont have enough armies to move");
                }
                else System.err.println("Error 10: The territory you want to move your army does not belong to you");
            } else System.err.println("Error 04: TerritoryId entered");
        }
        else System.err.println("Error 05: The two entered Territories are not neighbors");
    }

    /**
     * This Method performs an attack from a Terriory to another
     * @param fromTerritoryId int This is the Id of the attacking territory
     * @param toTerritoryId int This is the Id of the defending territory
     */     //todo: methode ausmisten
    @Override
    public boolean attack(int fromTerritoryId, int toTerritoryId) {
        if (isNeighbor(fromTerritoryId, toTerritoryId) && board.getArmy(fromTerritoryId) > 1) {
            int fromTerritoryControlledByPlayer = board.getControllingPlayerId(fromTerritoryId);
            int toTerritoryControlledByPlayer = board.getControllingPlayerId(toTerritoryId);

            int fromTerritoryArmyStrength = board.getArmy(fromTerritoryId) - 1;    //one army must stay behind
            int toTerritoryArmyStrength = board.getArmy(toTerritoryId);
            int attackStrength;
            int defenseStrength;
            int[] losses = {0, 0};

            if (fromTerritoryArmyStrength >= 4) {       //set the fighting armies
                attackStrength = 3;
            } else attackStrength = fromTerritoryArmyStrength;

            if (toTerritoryArmyStrength >= 2) {
                defenseStrength = 2;
            } else defenseStrength = toTerritoryArmyStrength;

            if (validTerritoryId(toTerritoryId) && validTerritoryId(fromTerritoryId)) {     //check if both territory ids are correct
                if (fromTerritoryControlledByPlayer != toTerritoryControlledByPlayer) {      //check if both territories belong to different players
                    losses = battle(attackStrength, defenseStrength);      //this line calculates the actual battle
                } else System.err.println("Error 06: You cannot attack your own terriory");
            } else System.err.println("Error 07: The territory Id is not valid");

            if (losses[0] > attackStrength) {
                losses[0] = attackStrength;
            }
            if (losses[1] > defenseStrength) {
                losses[1] = defenseStrength;
            }
            board.addArmy(fromTerritoryId, -losses[0]); // subtract the losses from the attacking territory
            board.addArmy(toTerritoryId, -losses[1]);      // subtract the losses from the defending territory


            if (board.getArmy(toTerritoryId) <= 0) {
                board.addArmy(fromTerritoryId, -1);
                board.addArmy(toTerritoryId, 1);
                board.setControllingPlayer(toTerritoryId, fromTerritoryControlledByPlayer);
                return true;
            }

        }
        else System.err.println("Error 08: The two entered Territories are not neighbors");

        return false;

        //todo: Nachziehen wenn alle Verteidiger besiegt implementieren
    }

    /**
     * This method returns the outcome of an attack in
     * @param attackers int number of armies attacking
     * @param defenders ind number of armies defending
     * @return This returns an int array with 2 entrys, [0]= number of lost attackers, [1]= number of lost defenders
     */
    private int[] battle(int attackers, int defenders) {
        int [] losses = new int[2];
        int [] attackThrow = rollD6(attackers);
        int [] defenenseThrow = rollD6(defenders);


        if (attackers < 2 || defenders < 2) {
            if (attackThrow[0] > defenenseThrow[0]) {
                losses[1]++;
            }
            else losses[0]++;
        }

        for (int i = 0; i < attackThrow.length; i++) {
            if (attackThrow[i] > defenenseThrow[i]) {
                losses[1]++;
            }
            else losses[0]++;
        }
        return losses;
    }

    /**
     * This method throws one six sided dice
     * @return This returns the result of the throw
     */
    public int rollD6() {
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }
    /**
     * This method rolls n six sided dice
     * @param n How many dice you roll, if n < 2 the second return array entry will be -1
     * @return This returns an sorted int[2], [0] = max, [1] = secMax
     */
    public int[] rollD6(int n) {
        int max = -1;
        int secMax = -1;

        for (int i = 0; i < n; i++) {
            int d6 = rollD6();
            if (max <= d6) {
                secMax = max;
                max = d6;
            }
        }
        return new int[]{max,secMax};
    }

    @Override
    public String[] getPlayerNames() {
        return new String[0];
    }

    @Override
    public void printTerritories() {
        board.printTerritories();
    }


    @Override
    public String getName(int id) {
       return board.getName(id);
    }

    @Override
    public int getControllingPlayerId(int territoryId) {
        return board.getControllingPlayerId(territoryId);
    }

    @Override
    public int getArmy(int territoryId) {
        return board.getArmy(territoryId);
    }

    @Override
    public String getPlayerName(int playerId) {
        return playerMap.get(playerId).getName();
    }

    @Override
    public Map<Integer,List<Point[]>> getTerritoryPointArrayMap() {
      return board.getTerritoryPointArrayMap();
    }

    @Override
    public Map<Integer, int[]> getNeighborsList() {
        return board.getNeighborsList();
    }

    @Override
    public boolean isGameOver() {
        return board.isGameOver();
    }

    @Override
    public boolean hasReinforcement(int playerId) {
        return getPlayer(playerId).hasReinforcement();
    }

    @Override
    public int countTerritories() {
        return board.countTerritories();
    }

    @Override
    public Point getCapital(int territoryId) {
        return board.getCapital(territoryId);
    }

    @Override
    public int compClaimTerritory() {
        int compTerrId = board.getRandomTerritoryId(-1);
        claimTerritory(2,compTerrId);
        return compTerrId;
    }

    @Override
    public int compMoveReinforcement() {
        int compTerrId = board.getRandomTerritoryId(2);
        int allCompArmy = getPlayer(2).getReinforcement();
        board.addArmy(compTerrId, allCompArmy);
        getPlayer(2).subReinforcement(allCompArmy);
        return compTerrId;
    }

    @Override
    public boolean allClaimed() {
        return board.isAllClaimed();    // checks if claim phase is over
    }


}
