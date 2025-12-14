package projet.model.potions;

import projet.model.Player;

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
