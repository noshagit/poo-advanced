package projet.armor;

public class Armor {
    private String name;
    private int resistance;
    private int weight;

    public Armor(String name, int resistance, int weight) {
        this.name = name;
        this.resistance = resistance;
        this.weight = weight;
    }

    public int getResistance() {
        return resistance;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }
}