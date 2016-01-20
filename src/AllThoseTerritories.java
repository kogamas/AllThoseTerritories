import game.Game;
import game.GameRun;

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
    //just for testing
    public static void main(String[] args) {
        System.out.println("---starting game---");
        Game game = new GameRun("Foo", "Bar", args);

        System.out.println("---printing initialized territories---");
        game.printTerritories();
        System.out.println("---claiming territories start---");
        claim(game);
        System.out.println("---claiming territories end---");
        System.out.println("---printing claimed territories---");
        game.printTerritories();
        System.out.println("---getName---");
        System.out.println("game.getName(1000):"+game.getName(1000));
        System.out.println("game.getName(1001):"+game.getName(1001));
        System.out.println("game.getName(1002):"+game.getName(1002));
        System.out.println("game.getName(2000):"+game.getName(2000));
        System.out.println("game.getName(2001):"+game.getName(2001));
        System.out.println("game.getName(3002):"+game.getName(3002));
        System.out.println("---calculating reinforcements---");    //todo: test this
        game.calculateReinforcement(1);
        game.calculateReinforcement(2);
        System.out.println("---reinforcing---");
        game.moveReinforcement(1,6004,14);
        game.moveReinforcement(2,6003,10);
        game.moveReinforcement(2,5006,8);
        game.moveReinforcement(2,5003,2);
        game.moveReinforcement(1,6002,1);
       // game.moveReinforcement(2,6001,1);
        System.out.println("---printing reinforced territories---");
        game.printTerritories();
        System.out.println("---attacking---");      //todo: test this
        game.attack(6004,6003);
        game.attack(6004,6003);
        game.attack(6004,6003);
        game.attack(6004,6003);
        game.attack(6004,6003);
        //game.attack(5002,4001);
        System.out.println("---printing attacked territories---");
        game.printTerritories();
        System.out.println("---moving---");         //todo: test this
        game.moveArmy(5003,5001,1);
        System.out.println("---printing moved army territories---");
        game.printTerritories();

       // game.printTerritories2();
    }
}
