package projet.model.potions;

import projet.model.Player;

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
