import java.util.ArrayList;


public class Continent {
    private String name;
    private int bonus;
    private ArrayList<Territory> territories = new ArrayList<>();


    public Continent(String name, int bonus) {
        this.name = name;
        this.bonus = bonus;
    }

    void addTerritory(Territory t) {
        this.territories.add(t);
    }
}
