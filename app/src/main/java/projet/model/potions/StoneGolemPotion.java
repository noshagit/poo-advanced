package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class StoneGolemPotion extends Potion {

    public StoneGolemPotion() {
        super("Stone Golem");
    }

    @Override
    public void use(Player player) {
        player.setExtraArmorGolem(player.getExtraArmorGolem() + 15);
    }
}
