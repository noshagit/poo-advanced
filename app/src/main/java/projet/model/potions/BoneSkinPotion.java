package projet.model.potions;

/* CLASS IMPORTS */

import projet.model.Player;

public class BoneSkinPotion extends Potion {
    public BoneSkinPotion() {
        super("Bone Skin");
    }

    @Override
    public void use(Player player) {
        player.setExtraArmor(5);
    }
}
