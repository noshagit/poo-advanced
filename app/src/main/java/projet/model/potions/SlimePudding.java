package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class SlimePudding extends Potion {

    public SlimePudding() {
        super("Slime Pudding");
    }

    @Override
    public void use(Player player) {
        player.setHealth(Math.max(player.getHealth() + 20, player.getMaxHealth()));
    }
    
}
