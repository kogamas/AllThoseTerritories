package Board;

import java.util.LinkedList;
import java.util.List;


class Continent {
    private String name;
    private int bonus;
    private int id;
    private List<Territory> territories = new LinkedList<>();


    Continent(String name, int bonus) {
        this.name = name;
        this.bonus = bonus;
    }

    /**
     * This method adds a Territory
     * @param t This Terriory will be added to this continent
     */
    void addTerritory(Territory t) {
        this.territories.add(t);
    }

    void setId(int id) {
        this.id = id;
    }

    int getId() {
        return id;
    }

    /**
     * This method returns a List of all Territories within
     * @return This is a List of all territories defining this continent
     */
    List<Territory> getTerritories() {
        return territories;
    }

    /**
     * This method returns the bonus value of the continent
     * @return This is the value of the contient
     */
    int getBonus() {
        return bonus;
    }

    public String getName() {
        return name;
    }
}
