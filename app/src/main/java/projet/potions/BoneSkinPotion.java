package projet.potions;

/* GAME CLASSES */

import projet.Player;

/** Class representing a BoneSkinPotion potion. */
public class BoneSkinPotion extends Potion {

    public BoneSkinPotion() {
        super("Bone Skin");
    }

    @Override
    public void use(Player player) {
        player.setExtraArmor(5);
    }
}
