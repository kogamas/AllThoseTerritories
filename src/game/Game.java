package game;


import readmapfiles.Reader;

import java.util.*;

public class Game implements GameIF{
    private World board;
    private int phaseOfGame = -1;
    private int roundOfPhase = -1;
    private Map<Integer, Player> playerMap = new HashMap<>();
    private Map<Integer, Territory> territoryMap;

    public Game(String player1, String player2){
        this.playerMap.put(1, new Player(player1));
        this.playerMap.put(2, new Player(player2));
    }


    public Territory getTerritory(int id) {
        if (territoryMap.containsKey(id)) {
            return territoryMap.get(id);
        }
        else return null;
    }

    private boolean validPlayerId(int playerId) {
        return playerMap.containsKey(playerId);
    }

    private boolean validTerritoryId(int territoryId) {
        return playerMap.containsKey(territoryId);
    }

    @Override
    public void startGame(String[] array) {
        this.board = new Reader(array).getWorld();
        this.phaseOfGame = 0;
        this.roundOfPhase = 0;
        this.territoryMap = this.board.getTerritoryMap();
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


    public void claimTerritory(int playerId, int territoryId) {
        Territory territory = territoryMap.get(territoryId);
        int territoryControlledByPlayer = territory.getControlledByPlayer();

        if (validPlayerId(playerId) && validTerritoryId(territoryId)) {
            if (territoryControlledByPlayer < 0) {
                territory.addArmy(1);
            }
        }
        else System.err.println("Error: invalid PlayerId or TerritoryId entered");
    }

    @Override
    public void moveReinforcement(int playerId, int territoryId, int numberOfArmies) {
        Territory territory = territoryMap.get(territoryId);
        int territoryControlledByPlayer = territory.getControlledByPlayer();

        if (validPlayerId(playerId) && validTerritoryId(territoryId)) {
            if (playerId == territoryControlledByPlayer) {
                territory.addArmy(numberOfArmies);
            }
        }
        else System.err.println("Error: invalid PlayerId or TerritoryId entered");
    }

    @Override
    public void moveArmy(int fromTerritoryId, int toTerritoryId, int numberOfArmies) {
        Territory fromTerritory = territoryMap.get(fromTerritoryId);
        Territory toTerritory = territoryMap.get(toTerritoryId);
        int fromTerritoryControlledByPlayer = fromTerritory.getControlledByPlayer();
        int toTerritoryControlledByPlayer = toTerritory.getControlledByPlayer();

        if (validTerritoryId(toTerritoryId) && validTerritoryId(fromTerritoryId)) {     //check if both territory ids are correct
            if (fromTerritoryControlledByPlayer == toTerritoryControlledByPlayer) {     //check if both territories belong to the same player
                if (fromTerritory.getArmy()>=numberOfArmies+1) {                        //check if enough armies are in territory
                    fromTerritory.subArmy(numberOfArmies);
                    toTerritory.addArmy(numberOfArmies);
                }
            }
        }
        else System.err.println("Error: TerritoryId entered");
    }

    /**
     * This Method performs an attack from a Terriory to another
     * @param fromTerritoryId int This is the Id of the attacking territory
     * @param toTerritoryId int This is the Id of the defending territory
     */     //todo: methode ausmisten
    @Override
    public void attack(int fromTerritoryId, int toTerritoryId) {
        Territory fromTerritory = territoryMap.get(fromTerritoryId);
        Territory toTerritory = territoryMap.get(toTerritoryId);
        int fromTerritoryControlledByPlayer = fromTerritory.getControlledByPlayer();
        int toTerritoryControlledByPlayer = toTerritory.getControlledByPlayer();
        int fromTerritoryArmyStrength = fromTerritory.getArmy() - 1;    //one army must stay behind
        int toTerritoryArmyStrength = toTerritory.getArmy();
        int attackStrength;
        int defenseStrength;
        int[] losses = {0,0};

        if (fromTerritoryArmyStrength >= 4) {       //set the fighting armies
            attackStrength = 3;
        }
        else attackStrength = fromTerritoryArmyStrength;

        if (toTerritoryArmyStrength >= 2)   {
            defenseStrength = 2;
        }
        else defenseStrength = toTerritoryArmyStrength;

        if (validTerritoryId(toTerritoryId) && validTerritoryId(fromTerritoryId)) {     //check if both territory ids are correct
            if (fromTerritoryControlledByPlayer != toTerritoryControlledByPlayer) {      //check if both territories belong to different players
               losses = battle(attackStrength,defenseStrength);      //this line calculates the actual battle
            }
            else System.err.println("Error: You cannot attack your own terriory");
        }
        else System.err.println("Error: The territory Id is not valid");

        fromTerritory.subArmy(losses[0]);    // subtract the losses from the attacking territory
        toTerritory.subArmy(losses[1]);      // subtract the losses from the defending territory

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
    @Override
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
}
