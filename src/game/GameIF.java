package game;

public interface GameIF {
    /**
     * methods for drawing the map
     */
  //  void createMap();   //creates a World class (Map) for playing the game// moved to startGame()
  //  void getMap();      //returns the points of the borders of the territories
  //  void getNeighbors();    //returns all Pairs pairs of neighbors ids there are

    /**
     * Game related methods
     */
    void startGame(String[] args);
  //  boolean isGameOver();
    void nextPhase();
    void nextRound();   //could check if GameOver

    /**
     * Army related methods
     */
    void moveReinforcement(int playerId, int territoryId, int numberOfArmies);     //sets an army on a territory
  //  void calculateReinforcement();

    void moveArmy(int fromTerritoryId, int toTerritoryId, int numberOfArmies);    //could return boolean if move is allowed
    void attack(int fromTerritoryId, int toTerritoryId);  //return must tell won, lost and how many casualties

    /**
     * miscellaneous methods
     *
     */
    int rollD6(); //returns int between 1-6
 //   void infoOfId(); //returns all necessery information to a specific Id (territory or continent)

    //space for more!!!
}
