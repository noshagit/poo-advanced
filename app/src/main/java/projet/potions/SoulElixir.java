package projet.potions;

/* GAME CLASSES */

import projet.Player;

/** Class representing a health potion. */
public class SoulElixir extends Potion {

    public SoulElixir() {
        super("Soul Elixir");
    }

    @Override
    public void use(Player player) {
        player.setLifeSteal(true);
    }
}
