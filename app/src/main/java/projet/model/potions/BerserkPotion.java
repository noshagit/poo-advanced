package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class BerserkPotion extends Potion {

    public BerserkPotion() {
        super("Berserk");
    }

    @Override
    public void use(Player player) {
        player.setExtraDamage(10);
        player.setHealth(player.getHealth() - 30);
    }
}
