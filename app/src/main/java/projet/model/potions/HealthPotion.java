package projet.model.potions;

import projet.model.Player;

/** Class representing a health potion. */
public class HealthPotion extends Potion {

    public HealthPotion() {
        super("Health");
    }

    @Override
    public void use(Player player) {
        player.setHealth(Math.min(player.getHealth() + 15, player.getMaxHealth()));
    }
}
