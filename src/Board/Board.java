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
    void subArmy(int territoryId, int n);
    public Map<Integer,Territory> getTerritoryMap();
    void printTerritories();

    boolean validTerritoryId(int territoryId);
    String getName(int id);
    public int calculateReinforcement(int playerId);
    public boolean isNeighborOf(int territoryId, int neighborId);
    public Map<Integer, int[]> getNeighborsList();
}
