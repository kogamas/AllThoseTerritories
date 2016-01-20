package game;

public class Player {
    private String name;
    private int reinforcement;

    public Player(String name){
        this.name = name;
        this.reinforcement = 0;
    }

    public String getName() {
        return name;
    }

    public int getReinforcement() {
        return reinforcement;
    }

    public void setReinforcement(int r) {
        this.reinforcement = r;
    }

    public void subReinforcement(int r) {
        this.reinforcement -= r;
    }
}
