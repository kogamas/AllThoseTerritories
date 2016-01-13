public interface Game {
    /**
     * methods for drawing the map
     */
    void createMap();   //creates a World class (Map) for playing the game
    void getMap();      //returns the points of the borders of the territories
    void getNeighbors();    //returns all Pairs pairs of neighbors there are

    /**
     * Game related methods
     */
    void startGame();
    boolean isGameOver();
    void nextPhase();
    void nextRound();   //could check if GameOver

    /**
     * Army related methods
     */
    void setArmy();     //sets an army on a territory
    void calculateReinforcements();

    void moveArmy(int fromId, int toId);    //could return boolean if move is allowed
    void attack(int fromId, int toId);  //return must tell won, lost and how many casualties

    /**
     * miscellaneous methods
     *
     */
    int rollDice(); //returns int between 1-6
    void infoOfId(); //returns all necessery information to a specific Id (territory or continent)

    //space for more!!!
}
