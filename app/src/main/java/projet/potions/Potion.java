package projet.potions;

/* GAME CLASSES */

import projet.Player;

/** Abstract class representing a potion. */
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
