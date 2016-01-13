package game;

import java.util.LinkedList;
import java.util.List;


public class Continent {
    private String name;
    private int bonus;
    private List<Territory> territories = new LinkedList<>();


    public Continent(String name, int bonus) {
        this.name = name;
        this.bonus = bonus;
    }

    /**
     * This method adds a Territory
     * @param t This Terriory will be added to this continent
     */
    public void addTerritory(Territory t) {
        this.territories.add(t);
    }

    /**
     * This method returns a List of all Territories within
     * @return This is a List of all territories defining this continent
     */
    public List<Territory> getTerritories() {
        return territories;
    }

    /**
     * This method returns the bonus value of the continent
     * @return This is the value of the contient
     */
    public int getBonus() {
        return bonus;
    }
}
