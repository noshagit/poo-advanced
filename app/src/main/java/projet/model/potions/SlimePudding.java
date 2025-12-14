package projet.model.potions;

import projet.model.Player;

/** Class representing a health potion. */
public class SlimePudding extends Potion {

    public SlimePudding() {
        super("Slime Pudding");
    }

    @Override
    public void use(Player player) {
        player.setHealth(Math.max(player.getHealth() + 20, player.getMaxHealth()));
    }
    
}
