package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public abstract class Potion {
    private final String name;

    public Potion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Player player);
}
