import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Territory {
    private String name;
    private Point capital;
    private ArrayList<Patch> patches = new ArrayList<>();
    private ArrayList<Territory> neighbours = new ArrayList<>();
    private int controlledByPlayer = -1;


    public Territory(String name, Point capital) {
        this.name = name;
        this.capital = capital;
    }

    void addPatch(Patch p) {
        this.patches.add(p);
    }

    void addNeighbour(Territory t) {
        this.neighbours.add(t);
    }

    void addNeighbour(Territory[] terArray) {
        for (Territory t : terArray) {
            List<Territory> newList = Arrays.asList(terArray);
            this.neighbours.addAll(newList);
        }
    }

    void setControlledByPlayer(int player) {
        this.controlledByPlayer = player;
    }
}
