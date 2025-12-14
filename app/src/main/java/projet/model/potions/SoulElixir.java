package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class SoulElixir extends Potion {

    public SoulElixir() {
        super("Soul Elixir");
    }

    @Override
    public void use(Player player) {
        player.setLifeSteal(true);
    }
}
