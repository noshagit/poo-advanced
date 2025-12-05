package projet.potions;

import projet.Player;

public class HealthPotion extends Potion {

    public HealthPotion() {
        super("Health Potion");
    }

    @Override
    public void use(Player player) {
        player.setHealth(player.getHealth() + 10);
    }
    
}
