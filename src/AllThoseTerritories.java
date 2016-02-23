import game.Game;
import game.GameRun;
import userinterface.InterfaceGui;

public class AllThoseTerritories  {

    public static void claim(Game game) {
        game.claimTerritory(2,1001);
        game.claimTerritory(2,1002);
        game.claimTerritory(2,1003);
        game.claimTerritory(2,1004);
        game.claimTerritory(2,1005);
        game.claimTerritory(2,1006);
        game.claimTerritory(2,1007);
        game.claimTerritory(2,1008);
        game.claimTerritory(2,1009);
        game.claimTerritory(1,2001);
        game.claimTerritory(1,2002);
        game.claimTerritory(1,2003);
        game.claimTerritory(1,2004);
        game.claimTerritory(2,3001);
        game.claimTerritory(2,3002);
        game.claimTerritory(2,3003);
        game.claimTerritory(2,3004);
        game.claimTerritory(2,3005);
        game.claimTerritory(2,3006);
        game.claimTerritory(2,3007);
        game.claimTerritory(1,4001);
        game.claimTerritory(1,4002);
        game.claimTerritory(1,4003);
        game.claimTerritory(1,4004);
        game.claimTerritory(1,4005);
        game.claimTerritory(1,4006);
        game.claimTerritory(1,4007);
        game.claimTerritory(1,4008);
        game.claimTerritory(1,4009);
        game.claimTerritory(1,4010);
        game.claimTerritory(1,4011);
        game.claimTerritory(1,4012);
        game.claimTerritory(2,5001);
        game.claimTerritory(2,5002);
        game.claimTerritory(2,5003);
        game.claimTerritory(2,5004);
        game.claimTerritory(2,5005);
        game.claimTerritory(2,5006);
        game.claimTerritory(1,6001);
        game.claimTerritory(1,6002);
        game.claimTerritory(2,6003);
        game.claimTerritory(1,6004);
    }
    public static void claim1(Game game) {
        game.claimTerritory(1,1001);
        game.claimTerritory(1,1002);
        game.claimTerritory(1,1003);
        game.claimTerritory(1,1004);
        game.claimTerritory(1,1005);
        game.claimTerritory(1,1006);
        game.claimTerritory(1,1007);
        game.claimTerritory(1,1008);
        game.claimTerritory(1,1009);
        game.claimTerritory(1,2001);
        game.claimTerritory(1,2002);
        game.claimTerritory(1,2003);
        game.claimTerritory(1,2004);
        game.claimTerritory(1,3001);
        game.claimTerritory(1,3002);
        game.claimTerritory(1,3003);
        game.claimTerritory(1,3004);
        game.claimTerritory(1,3005);
        game.claimTerritory(1,3006);
        game.claimTerritory(1,3007);
        game.claimTerritory(1,4001);
        game.claimTerritory(1,4002);
        game.claimTerritory(1,4003);
        game.claimTerritory(1,4004);
        game.claimTerritory(1,4005);
        game.claimTerritory(1,4006);
        game.claimTerritory(1,4007);
        game.claimTerritory(1,4008);
        game.claimTerritory(1,4009);
        game.claimTerritory(1,4010);
        game.claimTerritory(1,4011);
        game.claimTerritory(1,4012);
        game.claimTerritory(1,5001);
        game.claimTerritory(1,5002);
        game.claimTerritory(1,5003);
        game.claimTerritory(1,5004);
        game.claimTerritory(1,5005);
        game.claimTerritory(1,5006);
        game.claimTerritory(1,6001);
        game.claimTerritory(1,6002);
        game.claimTerritory(1,6003);
        game.claimTerritory(1,6004);
    }

    public void GameLogic(String [] args) {
        //initializing Game and generate Map from args
        String player1 = "Foo";//todo: get Player names from gui and insert here
        String player2 = "Bar";//todo: get Player names from gui and insert here
        Game game = new GameRun(player1, player2, args);

        //starting Phase 1: Landclaim
        for (int i = 0; i < game.countTerritories(); i++) {
            int territoryId = -1;
            int playerId = -1;
            if (i % 2 == 1) {
                game.claimTerritory(playerId, territoryId);     //todo: set territoryID and playerID by the gui
            }
            else {
                game.compClaimTerritory();
            }
        }
        //end of  landclaim Phase

        //starting Phase 2: conquest
        while (!game.isGameOver()) {

            game.calculateReinforcement(1);
            game.calculateReinforcement(2);

            while (game.hasReinforcement(1)) {
                int territoryId = -1;
                int numberOfArmies = -1;
               game.moveReinforcement(1, territoryId, 1);  //todo: set territoryID for each click
            }

            game.compMoveReinforcement();   //computer distributes his reinforcements when player is finished


            boolean player1BreakCondition = false;  //todo: get from gui (end turn button!!)
            while (!player1BreakCondition) {
                int fromTerritory = -1;
                int toTerritory = -1;

                game.attack(fromTerritory, toTerritory); //todo: set territoryIDs for each attack

                if (game.isGameOver()) {
                    System.out.println("Game over! Player 1 won!");   //todo: implement alert in Gui
                }
            }
        }
    }
    //just for testing
    public static void main(String[] args) {
        Game game = new GameRun("Foo", "Bar", args);
        InterfaceGui gui = new InterfaceGui(game);
    }
}
