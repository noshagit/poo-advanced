package projet.potions;

import projet.Player;

public abstract class Potion {
    private String name;

    public Potion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Player player);

}
