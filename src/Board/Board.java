package Board;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface Board {

    int getTerritoryId(Point p);
    Map<Integer,List<Point[]>> getTerritoryPointArrayMap();
    int getControllingPlayerId(int territoryId);
    void setControllingPlayer(int territoryId, int playerId);
    int getArmy(int territoryId);
    void addArmy(int territoryId, int n);
    Map<Integer,Territory> getTerritoryMap();
    void printTerritories();
    int countTerritories();
    boolean validTerritoryId(int territoryId);
    String getName(int id);
    int calculateReinforcement(int playerId);
    boolean isNeighborOf(int territoryId, int neighborId);
    Map<Integer, int[]> getNeighborsList();
    boolean isGameOver();
}
