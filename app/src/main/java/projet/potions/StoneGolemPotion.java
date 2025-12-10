package projet.potions;

/* GAME CLASSES */

import projet.Player;

/** Class representing a health potion. */
public class StoneGolemPotion extends Potion {

    public StoneGolemPotion() {
        super("Stone Golem");
    }

    @Override
    public void use(Player player) {
        player.setExtraArmorGolem(player.getExtraArmorGolem() + 15);
    }
}
