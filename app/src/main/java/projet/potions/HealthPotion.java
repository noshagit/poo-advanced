package projet.potions;

/* GAME CLASSES */

import projet.Player;

/** Class representing a health potion. */
public class HealthPotion extends Potion {

    public HealthPotion() {
        super("Health Potion");
    }

    @Override
    public void use(Player player) {
        player.setHealth(Math.max(player.getHealth() + 30, player.getMaxHealth()));
    }
    
}
